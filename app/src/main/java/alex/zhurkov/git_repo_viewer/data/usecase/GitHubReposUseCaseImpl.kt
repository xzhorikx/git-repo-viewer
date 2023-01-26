package alex.zhurkov.git_repo_viewer.data.usecase

import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoTimeframe
import alex.zhurkov.git_repo_viewer.domain.repository.GitHubRepoRepository
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase

class GitHubReposUseCaseImpl(
    private val gitHubRepository: GitHubRepoRepository
) : GitHubReposUseCase {

    override suspend fun getRepoPage(
        pageId: Int,
        repoTimeframe: RepoTimeframe,
        skipCache: Boolean
    ): GitHubReposPage.Timeframe = gitHubRepository.getRepoPage(
        pageId = pageId,
        repoTimeframe = repoTimeframe,
        skipCache = skipCache
    )

    override suspend fun getFavorites(pageId: Int): GitHubReposPage.Favorite? =
        gitHubRepository.getFavorites(pageId = pageId)
}
