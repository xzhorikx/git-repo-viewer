package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.BaseViewModel
import alex.zhurkov.git_repo_viewer.common.arch.Reducer
import alex.zhurkov.git_repo_viewer.common.arch.StateToModelMapper
import alex.zhurkov.git_repo_viewer.common.whenTrue
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase
import alex.zhurkov.git_repo_viewer.domain.usecase.NetworkConnectionUseCase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException

class MainActivityViewModel(
    private val gitHubReposUseCase: GitHubReposUseCase,
    private val networkConnectionUseCase: NetworkConnectionUseCase,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    reducer: Reducer<MainActivityState, MainActivityChange>,
    stateToModelMapper: StateToModelMapper<MainActivityState, MainActivityModel>
) : BaseViewModel<MainActivityAction, MainActivityChange, MainActivityState, MainActivityModel>(
    dispatcher = dispatcher, reducer = reducer, stateToModelMapper = stateToModelMapper
) {
    override var state = MainActivityState.EMPTY
    private var pageJob: Job? = null
    override fun onObserverActive(isFirstTime: Boolean) {
        super.onObserverActive(isFirstTime)
        if (isFirstTime) {
            state.nextPage?.let { loadRepoPage(pageIndex = it, repoFilter = state.repoFilter) }
        }
    }

    override fun onStateUpdated(oldState: MainActivityState, newState: MainActivityState) {
        super.onStateUpdated(oldState, newState)
        val isFilterChanged = oldState.repoFilter != newState.repoFilter
        val isLastVisibleItemUpdated = oldState.lastVisibleItemId != newState.lastVisibleItemId
        val shouldLoadNextPage =
            isLastVisibleItemUpdated && newState.lastRepoId == newState.lastVisibleItemId
        val isNetworkChanged =
            oldState.isNetworkConnected != null && (oldState.isNetworkConnected != newState.isNetworkConnected)

        if (shouldLoadNextPage || isFilterChanged) {
            state.nextPage?.let { loadRepoPage(pageIndex = it, repoFilter = newState.repoFilter) }
        }
        isNetworkChanged.whenTrue {
            newState.isNetworkConnected?.let {
                sendEvent(MainActivityEvent.NetworkConnectionChanged(it))
            }
        }
    }

    override suspend fun provideChangesObservable(): Flow<MainActivityChange> = merge(
        gitHubReposUseCase.observeFavorites().map { MainActivityChange.FavoritesChanged(it) },
        networkConnectionUseCase.observeConnectionState()
            .map { MainActivityChange.NetworkChanged(isConnected = it) }
    )

    override fun processAction(action: MainActivityAction) {
        when (action) {
            is MainActivityAction.FilterSelected -> {
                sendChange(MainActivityChange.FilterChanged(action.data))
            }
            MainActivityAction.Refresh -> refreshRepos()
            is MainActivityAction.LastVisibleItemChanged -> {
                if (action.id != state.lastVisibleItemId) {
                    sendChange(MainActivityChange.LastVisibleItemChanged(action.id))
                }
                Unit
            }
            is MainActivityAction.FavoriteClicked -> with(action.data) {
                handleFavoriteClick(id = id.toLong(), isCurrentFavorite = isFavorite)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        pageJob?.cancel()
    }

    private fun handleFavoriteClick(id: Long, isCurrentFavorite: Boolean) {
        viewModelScope.launch(dispatcher) {
            gitHubReposUseCase.saveFavorite(id = id, isFavorite = isCurrentFavorite.not())
        }
    }

    private fun refreshRepos() {
        sendChange(MainActivityChange.ItemsCleared)
        loadRepoPage(
            pageIndex = state.initialPageId,
            repoFilter = state.repoFilter,
            forceRefresh = true
        )
    }

    private fun loadRepoPage(
        pageIndex: Int,
        repoFilter: RepoFilter,
        forceRefresh: Boolean = false
    ) {
        when (forceRefresh) {
            true -> pageJob?.cancel(CancellationException("Force refresh"))
            false -> {
                if (state.isLastPageLoaded) return
                if (state.isPageLoading) return
                if (state.isPageLoaded(pageIndex)) return
            }
        }
        pageJob = viewModelScope.launch(dispatcher) {
            gitHubReposUseCase.getRepoPage(
                pageId = pageIndex,
                repoFilter = repoFilter,
                skipCache = forceRefresh
            )
                .distinctUntilChanged()
                .catch { e -> onPageLoadingError(e) }
                .onStart {
                    sendChange(MainActivityChange.PageLoadingChanged(isLoading = true))
                    forceRefresh.whenTrue {
                        sendChange(MainActivityChange.RefreshChanged(isRefreshing = true))
                    }
                }
                .onCompletion {
                    sendChange(MainActivityChange.PageLoadingChanged(isLoading = false))
                    forceRefresh.whenTrue {
                        sendChange(MainActivityChange.RefreshChanged(isRefreshing = false))
                    }
                }
                .onEach { sendChange(MainActivityChange.PageLoaded(it)) }
                .launchIn(this)
        }
    }

    private fun onPageLoadingError(e: Throwable) {
        sendEvent(MainActivityEvent.DisplayError(e))
        Timber.e(e)
    }
}
