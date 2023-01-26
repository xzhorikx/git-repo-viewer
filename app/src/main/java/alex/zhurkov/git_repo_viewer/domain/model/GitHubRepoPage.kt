package alex.zhurkov.git_repo_viewer.domain.model

sealed class GitHubReposPage {
    abstract val pageId: Int
    abstract val isLastPage: Boolean
    abstract val repos: List<GitHubRepo>

    data class Timeframe(
        override val pageId: Int,
        override val isLastPage: Boolean,
        override val repos: List<GitHubRepo>,
        val timeframe: RepoTimeframe
    ) : GitHubReposPage()

    data class Favorite(
        override val pageId: Int,
        override val isLastPage: Boolean,
        override val repos: List<GitHubRepo>
    ) : GitHubReposPage()
}
