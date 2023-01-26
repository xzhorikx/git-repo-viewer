package alex.zhurkov.git_repo_viewer.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteRepoInfoEntity(
    @Embedded val favoriteRepoEntity: FavoriteRepoEntity,
    @Relation(
        parentColumn = "favRepoId",
        entityColumn = "repoId",
        entity = GitHubRepoEntity::class
    )
    val repoEntity: GitHubRepoWithInfoEntity
)