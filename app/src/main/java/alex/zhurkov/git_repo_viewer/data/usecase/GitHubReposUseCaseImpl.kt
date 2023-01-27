package alex.zhurkov.git_repo_viewer.data.usecase

import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import alex.zhurkov.git_repo_viewer.domain.repository.GitHubRepoRepository
import alex.zhurkov.git_repo_viewer.domain.usecase.GitHubReposUseCase
import kotlinx.coroutines.flow.Flow

class GitHubReposUseCaseImpl(
    private val gitHubRepository: GitHubRepoRepository
) : GitHubReposUseCase {

    override suspend fun getRepoPage(
        pageId: Int,
        repoFilter: RepoFilter,
        skipCache: Boolean
    ): Flow<GitHubReposPage> = gitHubRepository.getRepoPage(
        pageId = pageId,
        repoFilter = repoFilter,
        skipCache = skipCache
    )

    override fun observeFavorites(): Flow<List<Long>> = gitHubRepository.observeFavorites()

    override suspend fun saveFavorite(id: Long, isFavorite: Boolean) =
        gitHubRepository.saveFavorite(
            id = id,
            isFavorite = isFavorite
        )

    override fun observeById(repoId: Long): Flow<GitHubRepo> = gitHubRepository.observeById(repoId)
}
