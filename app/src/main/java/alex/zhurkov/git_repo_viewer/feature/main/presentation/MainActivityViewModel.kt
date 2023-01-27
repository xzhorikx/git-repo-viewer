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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
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

        if (shouldLoadNextPage || isFilterChanged) {
            state.nextPage?.let { loadRepoPage(pageIndex = it, repoFilter = newState.repoFilter) }
        }
    }

    override suspend fun provideChangesObservable(): Flow<MainActivityChange> = emptyFlow()

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
            execute(
                action = {
                    gitHubReposUseCase.getRepoPage(
                        pageId = pageIndex,
                        repoFilter = repoFilter,
                        skipCache = forceRefresh
                    )
                },
                onStart = {
                    sendChange(MainActivityChange.PageLoadingChanged(isLoading = true))
                    forceRefresh.whenTrue {
                        sendChange(MainActivityChange.RefreshChanged(isRefreshing = true))
                    }
                },
                onComplete = {
                    sendChange(MainActivityChange.PageLoadingChanged(isLoading = false))
                    forceRefresh.whenTrue {
                        sendChange(MainActivityChange.RefreshChanged(isRefreshing = false))
                    }
                },
                onErrorOccurred = { onPageLoadingError(it) },
                onSuccess = { sendChange(MainActivityChange.PageLoaded(it)) }
            )
        }
    }

    private fun onPageLoadingError(e: Throwable) {
        sendEvent(MainActivityEvent.DisplayError(e))
        Timber.e(e)
    }
}
