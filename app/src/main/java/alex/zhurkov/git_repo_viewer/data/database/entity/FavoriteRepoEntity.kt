package alex.zhurkov.git_repo_viewer.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_repos",
    foreignKeys = [
        ForeignKey(
            entity = GitHubRepoEntity::class,
            parentColumns = ["repoId"],
            childColumns = ["favRepoId"],
            onDelete = ForeignKey.NO_ACTION
        ),
    ]
)
data class FavoriteRepoEntity(
    @PrimaryKey(autoGenerate = false) val favRepoId: Long
)
