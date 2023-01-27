package alex.zhurkov.git_repo_viewer.data.remote

import alex.zhurkov.git_repo_viewer.data.remote.model.GitHubPageResponse
import alex.zhurkov.git_repo_viewer.data.source.GitHubLocalSource
import alex.zhurkov.git_repo_viewer.domain.config.ConfigSource
import alex.zhurkov.git_repo_viewer.domain.mapper.Mapper
import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import alex.zhurkov.git_repo_viewer.domain.model.asIsoDateFromNow
import alex.zhurkov.git_repo_viewer.domain.repository.GitHubRepoRepository

private const val SORT_STARS = "stars"

class GitHubRepositoryImpl(
    private val configSource: ConfigSource,
    private val localSource: GitHubLocalSource,
    private val remoteSource: GitHubRemoteSource,
    private val repoRemoteMapper: Mapper<GitHubPageResponse, GitHubRepo>
) : GitHubRepoRepository {
    override suspend fun getRepoPage(
        pageId: Int,
        repoFilter: RepoFilter,
        skipCache: Boolean
    ): GitHubReposPage? {
        val limit = configSource.pageSize
        return when (repoFilter) {
            RepoFilter.Favorites -> getFavorites(pageId = pageId, limit = limit)
            is RepoFilter.TimeFrame -> {
                getTimeframePage(
                    pageId = pageId,
                    limit = limit,
                    repoFilter = repoFilter,
                    skipCache = skipCache
                )
            }
        }
    }

    private suspend fun getTimeframePage(
        pageId: Int,
        limit: Int,
        repoFilter: RepoFilter.TimeFrame,
        skipCache: Boolean
    ): GitHubReposPage {
        val localPage = when (skipCache) {
            true -> null
            false -> localSource.getPage(
                page = pageId, filter = repoFilter, limit = limit
            )
        }

        if (localPage != null && localPage.repos.size >= limit) return localPage

        val cacheControl = when (skipCache) {
            true -> "no-cache"
            false -> "public, max-stale=${configSource.cacheStaleSec}"
        }
        val response = remoteSource.getRepositoriesPage(
            q = "created:>${repoFilter.asIsoDateFromNow()}",
            sort = SORT_STARS,
            limit = configSource.pageSize,
            page = pageId,
            cacheControl = cacheControl
        ).items
        return GitHubReposPage(
            pageId = pageId,
            isLastPage = response.isEmpty(),
            repos = response.map(repoRemoteMapper::map),
            repoFilter = repoFilter
        ).also { localSource.savePage(it) }
    }

    private suspend fun getFavorites(pageId: Int, limit: Int): GitHubReposPage? =
        localSource.getFavorites(page = pageId, limit = limit)

}