package alex.zhurkov.git_repo_viewer.feature.main.model

import alex.zhurkov.git_repo_viewer.domain.model.Owner

data class OwnerItem(
    val id: Long,
    val login: String,
    val url: String,
    val avatar: String
)

fun Owner.toItem() = OwnerItem(
    id = id,
    login = login,
    url = url,
    avatar = avatar
)
