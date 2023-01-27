package alex.zhurkov.git_repo_viewer.domain.repository

import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import kotlinx.coroutines.flow.Flow

// Great naming, I dig it
interface GitHubRepoRepository {
    suspend fun getRepoPage(
        pageId: Int,
        repoFilter: RepoFilter,
        skipCache: Boolean
    ): Flow<GitHubReposPage>

    /**
     * Returns flow of all saved favorite IDs
     */
    fun observeFavorites(): Flow<List<Long>>

    suspend fun saveFavorite(id: Long, isFavorite: Boolean)

    fun observeById(repoId: Long): Flow<GitHubRepo>
}
