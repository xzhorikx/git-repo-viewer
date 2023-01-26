package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.BaseViewModel
import alex.zhurkov.git_repo_viewer.common.arch.Reducer
import alex.zhurkov.git_repo_viewer.common.arch.StateToModelMapper
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase
import alex.zhurkov.git_repo_viewer.domain.usecase.NetworkConnectionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MainActivityViewModel(
    private val GitHubReposUseCase: GitHubReposUseCase,
    private val networkConnectionUseCase: NetworkConnectionUseCase,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    reducer: Reducer<MainActivityState, MainActivityChange>,
    stateToModelMapper: StateToModelMapper<MainActivityState, MainActivityModel>
) : BaseViewModel<MainActivityAction, MainActivityChange, MainActivityState, MainActivityModel>(
    dispatcher = dispatcher, reducer = reducer, stateToModelMapper = stateToModelMapper
) {
    override var state = MainActivityState.EMPTY

    override suspend fun provideChangesObservable(): Flow<MainActivityChange> = emptyFlow()

    override fun processAction(action: MainActivityAction) = Unit
}
