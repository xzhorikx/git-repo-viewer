package alex.zhurkov.git_repo_viewer.domain.model

data class GitHubRepo(
    val id: Long,
    val owner: Owner,
    val name: String,
    val url: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val stars: Int,
)