package alex.zhurkov.git_repo_viewer.data.database.dao

import alex.zhurkov.git_repo_viewer.data.database.entity.FavoriteRepoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.FavoriteRepoInfoEntity
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorite_repos LIMIT :limit OFFSET :offset")
    suspend fun get(limit: Int, offset: Int): List<FavoriteRepoInfoEntity>

    @Query("SELECT * FROM favorite_repos")
    fun observe(): Flow<List<FavoriteRepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: FavoriteRepoEntity)

    @Delete
    suspend fun delete(entity: FavoriteRepoEntity)
}
