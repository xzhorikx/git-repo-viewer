package alex.zhurkov.git_repo_viewer.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "git_hub_repo")
data class GitHubRepoEntity(
    @PrimaryKey(autoGenerate = false) val repoId: Long,
    val ownerId: Long,
    val name: String,
    val url: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val stars: Int
)
