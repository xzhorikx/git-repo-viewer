package alex.zhurkov.git_repo_viewer.data.remote

import alex.zhurkov.git_repo_viewer.data.remote.model.GitHubRepoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GitHubRemoteSource {
    @GET("search/repositories")
    suspend fun getRepositoriesPage(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int,
        @Query("sort") sort: String,
        @Header("Cache-Control") cacheControl: String
    ): List<GitHubRepoResponse>
}
