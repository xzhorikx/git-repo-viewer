package alex.zhurkov.git_repo_viewer.domain.model

data class Owner(
    val id: Long,
    val login: String,
    val url: String,
    val avatar: String
)
