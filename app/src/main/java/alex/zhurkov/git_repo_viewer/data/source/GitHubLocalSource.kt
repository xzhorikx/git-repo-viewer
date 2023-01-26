package alex.zhurkov.git_repo_viewer.data.source

import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter

interface GitHubLocalSource {
    suspend fun savePage(page: GitHubReposPage)

    /**
     * Returns a page with the GitHub repositories. [page] param should be GTE 1
     */
    suspend fun getPage(
        page: Int,
        filter: RepoFilter.TimeFrame,
        limit: Int
    ): GitHubReposPage?

    /**
     * Returns a page with saved favorite repositories
     */
    suspend fun getFavorites(page: Int, limit: Int): GitHubReposPage?

    suspend fun addFavorite(repoId: Long)

    suspend fun removeFavorite(repoId: Long)
}
