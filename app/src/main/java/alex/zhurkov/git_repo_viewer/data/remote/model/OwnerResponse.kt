package alex.zhurkov.git_repo_viewer.data.remote.model

import com.google.gson.annotations.SerializedName

data class OwnerResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("html_url") val url: String,
    @SerializedName("avatar_url") val avatar: String
)