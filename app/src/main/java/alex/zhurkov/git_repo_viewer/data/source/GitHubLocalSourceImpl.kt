package alex.zhurkov.git_repo_viewer.data.source

import alex.zhurkov.git_repo_viewer.data.database.AppDatabase
import alex.zhurkov.git_repo_viewer.data.database.entity.FavoriteRepoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.FavoriteRepoInfoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoWithInfoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.RepoFilterRelationEntity
import alex.zhurkov.git_repo_viewer.data.database.mapper.EntityMapper
import alex.zhurkov.git_repo_viewer.domain.config.ConfigSource
import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubLocalSourceImpl(
    private val database: AppDatabase,
    private val repoMapper: EntityMapper<GitHubRepo, GitHubRepoWithInfoEntity>,
    private val configSource: ConfigSource
) : GitHubLocalSource {
    override suspend fun savePage(page: GitHubReposPage) {
        page.repos.forEach {
            val entity = repoMapper.toEntity(it)
            database.ownerDao().save(entity.owner)
            database.repoDao().save(entity.gitHubRepoEntity)
            database.repoDao().saveFilter(
                RepoFilterRelationEntity(
                    repoFilter = page.repoFilter,
                    repoIdRef = entity.gitHubRepoEntity.repoId
                )
            )
        }
    }

    override suspend fun getPage(
        page: Int,
        filter: RepoFilter.TimeFrame,
        limit: Int
    ): GitHubReposPage? {
        if (page <= 0) return null
        val offset = (page - 1) * configSource.pageSize
        val repos = database.repoDao().get(
            filter, limit = limit, offset = offset
        ).map(repoMapper::toModel).takeIf { it.isNotEmpty() } ?: return null

        return GitHubReposPage(
            pageId = page, isLastPage = repos.size < limit, repoFilter = filter, repos = repos
        )
    }

    override suspend fun getFavorites(page: Int, limit: Int): GitHubReposPage? {
        val offset = (page - 1) * configSource.pageSize
        val repos = database.favoritesDao().get(limit = limit, offset = offset)
            .map(FavoriteRepoInfoEntity::repoEntity).map(repoMapper::toModel)

        return GitHubReposPage(
            pageId = page,
            isLastPage = repos.size < limit,
            repos = repos,
            repoFilter = RepoFilter.Favorites
        )
    }

    override fun observeFavorites(): Flow<List<Long>> =
        database.favoritesDao().observe().map { it.map(FavoriteRepoEntity::favRepoId) }

    override suspend fun addFavorite(repoId: Long) =
        database.favoritesDao().save(FavoriteRepoEntity(favRepoId = repoId))

    override suspend fun removeFavorite(repoId: Long) =
        database.favoritesDao().delete(FavoriteRepoEntity(favRepoId = repoId))

    override fun observeById(repoId: Long): Flow<GitHubRepo> =
        database.repoDao().observeById(repoId).map(repoMapper::toModel)
}