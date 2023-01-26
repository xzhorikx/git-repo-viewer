package alex.zhurkov.git_repo_viewer.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GitHubRepoWithInfoEntity(
    @Embedded
    val gitHubRepoEntity: GitHubRepoEntity,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "id",
        entity = OwnerEntity::class
    )
    val owner: OwnerEntity
)
