package alex.zhurkov.git_repo_viewer.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owner")
data class OwnerEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val login: String,
    val url: String,
    val avatar: String
)
