package alex.zhurkov.git_repo_viewer.domain.repository

import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter

// Great naming, I dig it
interface GitHubRepoRepository {
    suspend fun getRepoPage(
        pageId: Int,
        repoFilter: RepoFilter,
        skipCache: Boolean
    ): GitHubReposPage?
}
