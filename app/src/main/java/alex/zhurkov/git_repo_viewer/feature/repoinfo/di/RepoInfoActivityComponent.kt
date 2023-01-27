package alex.zhurkov.git_repo_viewer.feature.repoinfo.di

import alex.zhurkov.git_repo_viewer.app.di.ActivityScope
import alex.zhurkov.git_repo_viewer.domain.di.GitHubDomainModule
import alex.zhurkov.git_repo_viewer.domain.di.NetworkConnectionModule
import alex.zhurkov.git_repo_viewer.feature.repoinfo.RepoInfoActivity
import alex.zhurkov.git_repo_viewer.feature.repoinfo.model.RepoInfoInputData
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        GitHubDomainModule::class,
        NetworkConnectionModule::class,
        RepoInfoActivityPresentationModule::class
    ]
)
interface RepoInfoActivityComponent {

    fun inject(target: RepoInfoActivity)

    interface ComponentProvider {
        fun provideMainComponent(
            activity: RepoInfoActivity,
            inputData: RepoInfoInputData
        ): RepoInfoActivityComponent
    }

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: RepoInfoActivity,
            @BindsInstance inputData: RepoInfoInputData
        ): RepoInfoActivityComponent
    }
}