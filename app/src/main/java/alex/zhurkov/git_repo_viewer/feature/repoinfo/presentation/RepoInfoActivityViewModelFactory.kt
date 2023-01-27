package alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation

import alex.zhurkov.git_repo_viewer.common.arch.Reducer
import alex.zhurkov.git_repo_viewer.common.arch.StateToModelMapper
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase
import alex.zhurkov.git_repo_viewer.feature.repoinfo.model.RepoInfoInputData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RepoInfoActivityViewModelFactory(
    private val inputData: RepoInfoInputData,
    private val gitHubReposUseCase: GitHubReposUseCase,
    private val reducer: Reducer<RepoInfoActivityState, RepoInfoActivityChange>,
    private val stateToModelMapper: StateToModelMapper<RepoInfoActivityState, RepoInfoActivityModel>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = RepoInfoActivityViewModel(
        inputData = inputData,
        gitHubReposUseCase = gitHubReposUseCase,
        reducer = reducer,
        stateToModelMapper = stateToModelMapper
    ) as T
}
