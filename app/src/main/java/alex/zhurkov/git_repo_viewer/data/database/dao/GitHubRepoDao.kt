package alex.zhurkov.git_repo_viewer.data.database.dao

import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoWithInfoEntity
import androidx.room.*

@Dao
interface GitHubRepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: GitHubRepoEntity)

    @Transaction
    @Query("SELECT * FROM git_hub_repo WHERE createdAt > :createdAtMin LIMIT :limit OFFSET :offset")
    suspend fun get(createdAtMin: String, limit: Int, offset: Int): List<GitHubRepoWithInfoEntity>
}
