package alex.zhurkov.git_repo_viewer.data.database

import alex.zhurkov.git_repo_viewer.data.database.dao.FavoritesDao
import alex.zhurkov.git_repo_viewer.data.database.dao.GitHubRepoDao
import alex.zhurkov.git_repo_viewer.data.database.dao.OwnerDao
import alex.zhurkov.git_repo_viewer.data.database.entity.FavoriteRepoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.OwnerEntity
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
        OwnerEntity::class,
        GitHubRepoEntity::class,
        FavoriteRepoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ownerDao(): OwnerDao
    abstract fun repoDao(): GitHubRepoDao
    abstract fun favoritesDao(): FavoritesDao
}
