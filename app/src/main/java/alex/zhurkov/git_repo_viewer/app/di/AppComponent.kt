package alex.zhurkov.git_repo_viewer.app.di

import alex.zhurkov.git_repo_viewer.app.GitHubRepoViewerApplication
import alex.zhurkov.git_repo_viewer.feature.main.di.MainActivityComponent
import alex.zhurkov.git_repo_viewer.feature.repoinfo.di.RepoInfoActivityComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        GitHubDataModule::class,
        ImageLoaderModule::class
    ]
)
interface AppComponent {
    fun inject(target: GitHubRepoViewerApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: GitHubRepoViewerApplication): AppComponent
    }

    fun plusMainActivityComponent(): MainActivityComponent.Factory
    fun plusRepoInfoActivityComponent(): RepoInfoActivityComponent.Factory
}