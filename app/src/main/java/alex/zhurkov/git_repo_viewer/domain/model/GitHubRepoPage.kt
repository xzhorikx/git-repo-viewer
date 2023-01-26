package alex.zhurkov.git_repo_viewer.domain.model

data class GitHubReposPage(
    val pageId: Int,
    val isLastPage: Boolean,
    val repos: List<GitHubRepo>,
    val repoFilter: RepoFilter
)
