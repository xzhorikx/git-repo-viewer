package alex.zhurkov.git_repo_viewer.data.database.entity

import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * 1:1 Relation of search filter to the repository
 */
@Entity(
    tableName = "repo_filter",
    foreignKeys = [
        ForeignKey(
            entity = GitHubRepoEntity::class,
            parentColumns = ["repoId"],
            childColumns = ["repoIdRef"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["repoFilter", "repoIdRef"]
)
data class RepoFilterRelationEntity(
    val repoFilter: RepoFilter,
    val repoIdRef: Long
)
