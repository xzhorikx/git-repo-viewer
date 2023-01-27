package alex.zhurkov.git_repo_viewer.feature.repoinfo.di

import alex.zhurkov.git_repo_viewer.app.di.ActivityScope
import alex.zhurkov.git_repo_viewer.common.arch.Reducer
import alex.zhurkov.git_repo_viewer.common.arch.StateToModelMapper
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase
import alex.zhurkov.git_repo_viewer.feature.repoinfo.model.RepoInfoInputData
import alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation.*
import dagger.Module
import dagger.Provides

@Module
class RepoInfoActivityPresentationModule {

    @Provides
    @ActivityScope
    fun reducer(): Reducer<RepoInfoActivityState, RepoInfoActivityChange> =
        RepoInfoActivityReducer()

    @Provides
    @ActivityScope
    fun stateToModelMapper(): StateToModelMapper<RepoInfoActivityState, RepoInfoActivityModel> =
        RepoInfoStateToModelMapper()

    @Provides
    @ActivityScope
    fun viewModelFactory(
        inputData: RepoInfoInputData,
        gitHubReposUseCase: GitHubReposUseCase,
        reducer: Reducer<RepoInfoActivityState, RepoInfoActivityChange>,
        stateToModelMapper: StateToModelMapper<RepoInfoActivityState, RepoInfoActivityModel>
    ) = RepoInfoActivityViewModelFactory(
        inputData = inputData,
        gitHubReposUseCase = gitHubReposUseCase,
        reducer = reducer,
        stateToModelMapper = stateToModelMapper
    )
}
