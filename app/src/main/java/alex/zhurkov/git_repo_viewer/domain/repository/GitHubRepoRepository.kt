package alex.zhurkov.git_repo_viewer.domain.repository

import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoTimeframe

// Great naming, I dig it
interface GitHubRepoRepository {
    suspend fun getRepoPage(
        pageId: Int,
        repoTimeframe: RepoTimeframe,
        skipCache: Boolean
    ): GitHubReposPage.Timeframe

    suspend fun getFavorites(pageId: Int): GitHubReposPage.Favorite?
}
