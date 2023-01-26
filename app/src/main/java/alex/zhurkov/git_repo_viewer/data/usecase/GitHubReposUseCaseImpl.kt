package alex.zhurkov.git_repo_viewer.data.usecase

import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import alex.zhurkov.git_repo_viewer.domain.repository.GitHubRepoRepository
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase

class GitHubReposUseCaseImpl(
    private val gitHubRepository: GitHubRepoRepository
) : GitHubReposUseCase {

    override suspend fun getRepoPage(
        pageId: Int,
        repoFilter: RepoFilter,
        skipCache: Boolean
    ): GitHubReposPage? = gitHubRepository.getRepoPage(
        pageId = pageId,
        repoFilter = repoFilter,
        skipCache = skipCache
    )
}
