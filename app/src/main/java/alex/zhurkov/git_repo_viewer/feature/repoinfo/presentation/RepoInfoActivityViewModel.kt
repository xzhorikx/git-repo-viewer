package alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation

import alex.zhurkov.git_repo_viewer.common.arch.BaseViewModel
import alex.zhurkov.git_repo_viewer.common.arch.Reducer
import alex.zhurkov.git_repo_viewer.common.arch.StateToModelMapper
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase
import alex.zhurkov.git_repo_viewer.feature.repoinfo.model.RepoInfoInputData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class RepoInfoActivityViewModel(
    private val inputData: RepoInfoInputData,
    private val gitHubReposUseCase: GitHubReposUseCase,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    reducer: Reducer<RepoInfoActivityState, RepoInfoActivityChange>,
    stateToModelMapper: StateToModelMapper<RepoInfoActivityState, RepoInfoActivityModel>
) : BaseViewModel<RepoInfoActivityAction, RepoInfoActivityChange, RepoInfoActivityState, RepoInfoActivityModel>(
    dispatcher = dispatcher, reducer = reducer, stateToModelMapper = stateToModelMapper
) {
    override var state = RepoInfoActivityState.EMPTY
    override suspend fun provideChangesObservable(): Flow<RepoInfoActivityChange> =
        merge(
            gitHubReposUseCase.observeById(inputData.repoId)
                .map { RepoInfoActivityChange.RepoChanged(it) },
            gitHubReposUseCase.observeFavorites()
                .map { RepoInfoActivityChange.FavoritesChanged(it) },
        )

    override fun processAction(action: RepoInfoActivityAction) {
        when (action) {
            is RepoInfoActivityAction.FavoriteClicked -> with(action.data) {
                handleFavoriteClick(id = id.toLong(), isCurrentFavorite = isFavorite)
            }
        }
    }

    private fun handleFavoriteClick(id: Long, isCurrentFavorite: Boolean) {
        viewModelScope.launch(dispatcher) {
            gitHubReposUseCase.saveFavorite(id = id, isFavorite = isCurrentFavorite.not())
        }
    }

}
