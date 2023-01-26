package alex.zhurkov.git_repo_viewer.data.database.dao

import alex.zhurkov.git_repo_viewer.data.database.entity.OwnerEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface OwnerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: OwnerEntity)
}
