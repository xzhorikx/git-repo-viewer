package alex.zhurkov.git_repo_viewer.domain.usecase

import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoTimeframe

interface GitHubReposUseCase {
    suspend fun getRepoPage(
        pageId: Int,
        repoTimeframe: RepoTimeframe,
        skipCache: Boolean
    ): GitHubReposPage.Timeframe

    suspend fun getFavorites(pageId: Int): GitHubReposPage.Favorite?
}
