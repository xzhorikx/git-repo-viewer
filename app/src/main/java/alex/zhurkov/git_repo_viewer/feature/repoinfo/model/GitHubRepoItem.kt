package alex.zhurkov.git_repo_viewer.feature.repoinfo.model

import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import java.util.*


sealed class GitHubRepoInfoItem {
    abstract val id: String

    data class Data(
        override val id: String,
        val owner: OwnerItem,
        val name: String,
        val url: String,
        val description: String?,
        val stars: Int,
        val isFavorite: Boolean
    ) : GitHubRepoInfoItem()

    data class Loading(override val id: String = UUID.randomUUID().toString()) : GitHubRepoInfoItem()
}

fun GitHubRepo.toItem(isFavorite: Boolean): GitHubRepoInfoItem.Data = GitHubRepoInfoItem.Data(
    id = id.toString(),
    owner = owner.toItem(),
    name = name,
    url = url,
    description = description.takeIf { it.isNotEmpty() },
    stars = stars,
    isFavorite = isFavorite
)
