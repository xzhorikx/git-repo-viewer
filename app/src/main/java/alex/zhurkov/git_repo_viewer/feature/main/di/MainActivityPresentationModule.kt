package alex.zhurkov.git_repo_viewer.feature.main.di

import alex.zhurkov.git_repo_viewer.app.di.ActivityScope
import alex.zhurkov.git_repo_viewer.common.arch.Reducer
import alex.zhurkov.git_repo_viewer.common.arch.StateToModelMapper
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase
import alex.zhurkov.git_repo_viewer.domain.usecase.NetworkConnectionUseCase
import alex.zhurkov.git_repo_viewer.feature.main.presentation.*
import dagger.Module
import dagger.Provides

@Module
class MainActivityPresentationModule {

    @Provides
    @ActivityScope
    fun reducer(): Reducer<MainActivityState, MainActivityChange> = MainActivityReducer()

    @Provides
    @ActivityScope
    fun stateToModelMapper(): StateToModelMapper<MainActivityState, MainActivityModel> =
        MainStateToModelMapper()

    @Provides
    @ActivityScope
    fun viewModelFactory(
        GitHubReposUseCase: GitHubReposUseCase,
        networkConnectionUseCase: NetworkConnectionUseCase,
        reducer: Reducer<MainActivityState, MainActivityChange>,
        stateToModelMapper: StateToModelMapper<MainActivityState, MainActivityModel>
    ) = MainActivityViewModelFactory(
        GitHubReposUseCase = GitHubReposUseCase,
        networkConnectionUseCase = networkConnectionUseCase,
        reducer = reducer,
        stateToModelMapper = stateToModelMapper
    )
}
