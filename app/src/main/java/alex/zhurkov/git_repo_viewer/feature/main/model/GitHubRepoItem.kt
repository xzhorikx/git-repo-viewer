package alex.zhurkov.git_repo_viewer.feature.main.model

import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import java.util.*


sealed class GitHubRepoItem {
    abstract val id: String

    data class Data(
        override val id: String,
        val owner: OwnerItem,
        val name: String,
        val url: String,
        val description: String?,
        val stars: Int
    ) : GitHubRepoItem()

    data class Loading(override val id: String = UUID.randomUUID().toString()) : GitHubRepoItem()
}

fun GitHubRepo.toItem(): GitHubRepoItem.Data = GitHubRepoItem.Data(
    id = id.toString(),
    owner = owner.toItem(),
    name = name,
    url = url,
    description = description.takeIf { it.isNotEmpty() },
    stars = stars
)
