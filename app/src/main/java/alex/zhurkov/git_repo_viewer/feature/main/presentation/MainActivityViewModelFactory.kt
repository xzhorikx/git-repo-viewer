package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.Reducer
import alex.zhurkov.git_repo_viewer.common.arch.StateToModelMapper
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase
import alex.zhurkov.git_repo_viewer.domain.usecase.NetworkConnectionUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivityViewModelFactory(
    private val gitHubReposUseCase: GitHubReposUseCase,
    private val networkConnectionUseCase: NetworkConnectionUseCase,
    private val reducer: Reducer<MainActivityState, MainActivityChange>,
    private val stateToModelMapper: StateToModelMapper<MainActivityState, MainActivityModel>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainActivityViewModel(
        gitHubReposUseCase = gitHubReposUseCase,
        networkConnectionUseCase = networkConnectionUseCase,
        reducer = reducer,
        stateToModelMapper = stateToModelMapper
    ) as T
}
