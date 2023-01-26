package alex.zhurkov.git_repo_viewer.domain.di

import alex.zhurkov.git_repo_viewer.data.usecase.GitHubReposUseCaseImpl
import alex.zhurkov.git_repo_viewer.domain.repository.GitHubRepoRepository
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class GitHubDomainModule {

    @Provides
    @Reusable
    fun GitHubReposUseCase(
        gitHubRepository: GitHubRepoRepository
    ): GitHubReposUseCase = GitHubReposUseCaseImpl(
        gitHubRepository = gitHubRepository
    )

}
