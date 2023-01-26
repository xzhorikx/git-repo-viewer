package alex.zhurkov.git_repo_viewer.domain.usecase

import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter

interface GitHubReposUseCase {
    suspend fun getRepoPage(
        pageId: Int,
        repoFilter: RepoFilter,
        skipCache: Boolean
    ): GitHubReposPage?
}
