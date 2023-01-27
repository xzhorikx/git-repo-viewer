package alex.zhurkov.git_repo_viewer.data.remote.model

import com.google.gson.annotations.SerializedName

data class GitHubRepoResponse(
    @SerializedName("items") val items: List<GitHubPageResponse>
)

data class GitHubPageResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("owner") val owner: OwnerResponse,
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val url: String,
    @SerializedName("description") val description: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("forks") val forks: Int,
    @SerializedName("language") val language: String?
)