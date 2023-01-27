package alex.zhurkov.git_repo_viewer.data.database

import alex.zhurkov.git_repo_viewer.data.database.converter.FilterConverter
import alex.zhurkov.git_repo_viewer.data.database.dao.FavoritesDao
import alex.zhurkov.git_repo_viewer.data.database.dao.GitHubRepoDao
import alex.zhurkov.git_repo_viewer.data.database.dao.OwnerDao
import alex.zhurkov.git_repo_viewer.data.database.entity.FavoriteRepoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.OwnerEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.RepoFilterRelationEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [
        OwnerEntity::class,
        GitHubRepoEntity::class,
        FavoriteRepoEntity::class,
        RepoFilterRelationEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
    FilterConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ownerDao(): OwnerDao
    abstract fun repoDao(): GitHubRepoDao
    abstract fun favoritesDao(): FavoritesDao
}
