package alex.zhurkov.git_repo_viewer.data.database.dao

import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoWithInfoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.RepoFilterRelationEntity
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GitHubRepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: GitHubRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilter(data: RepoFilterRelationEntity)

    @Transaction
    @Query(
        """
        SELECT * FROM repo_filter as RF
        INNER JOIN git_hub_repo as REPO
        ON RF.repoIdRef = REPO.repoId
        WHERE RF.repoFilter = :repoFilter
        ORDER BY REPO.stars DESC LIMIT :limit OFFSET :offset
        """
    )
    suspend fun get(
        repoFilter: RepoFilter,
        limit: Int,
        offset: Int
    ): List<GitHubRepoWithInfoEntity>


    @Query("SELECT * FROM git_hub_repo where repoId = :id")
    fun observeById(id: Long): Flow<GitHubRepoWithInfoEntity>

}
