package alex.zhurkov.git_repo_viewer.data.remote

import alex.zhurkov.git_repo_viewer.data.remote.model.GitHubRepoResponse
import alex.zhurkov.git_repo_viewer.data.source.GitHubLocalSource
import alex.zhurkov.git_repo_viewer.domain.config.ConfigSource
import alex.zhurkov.git_repo_viewer.domain.mapper.Mapper
import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoTimeframe
import alex.zhurkov.git_repo_viewer.domain.model.asIsoDateFromNow
import alex.zhurkov.git_repo_viewer.domain.repository.GitHubRepoRepository

private const val SORT_STARS = "stars"

class GitHubRepositoryImpl(
    private val configSource: ConfigSource,
    private val localSource: GitHubLocalSource,
    private val remoteSource: GitHubRemoteSource,
    private val repoRemoteMapper: Mapper<GitHubRepoResponse, GitHubRepo>
) : GitHubRepoRepository {
    override suspend fun getRepoPage(
        pageId: Int,
        repoTimeframe: RepoTimeframe,
        skipCache: Boolean
    ): GitHubReposPage.Timeframe {
        val limit = configSource.pageSize
        val localPage = when (skipCache) {
            true -> null
            false -> localSource.getPage(
                page = pageId, repoTimeframe = repoTimeframe, limit = limit
            )
        }

        if (localPage != null) return localPage

        val cacheControl = when (skipCache) {
            true -> "no-cache"
            false -> "public, max-stale=${configSource.cacheStaleSec}"
        }
        val response = remoteSource.getRepositoriesPage(
            q = "created: >${repoTimeframe.asIsoDateFromNow()}",
            sort = SORT_STARS,
            limit = configSource.pageSize,
            page = pageId,
            cacheControl = cacheControl
        )
        return GitHubReposPage.Timeframe(
            pageId = pageId,
            isLastPage = response.isEmpty(),
            repos = response.map(repoRemoteMapper::map),
            timeframe = repoTimeframe
        ).also { localSource.savePage(it) }
    }

    override suspend fun getFavorites(pageId: Int): GitHubReposPage.Favorite? =
        localSource.getFavorites(page = pageId, limit = configSource.pageSize)

}