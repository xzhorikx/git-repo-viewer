package alex.zhurkov.git_repo_viewer.app.di

import alex.zhurkov.git_repo_viewer.data.database.AppDatabase
import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoWithInfoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.OwnerEntity
import alex.zhurkov.git_repo_viewer.data.database.mapper.EntityMapper
import alex.zhurkov.git_repo_viewer.data.database.mapper.GitHubRepoEntityMapper
import alex.zhurkov.git_repo_viewer.data.database.mapper.OwnerEntityMapper
import alex.zhurkov.git_repo_viewer.data.mapper.GitHubRepoResponseMapper
import alex.zhurkov.git_repo_viewer.data.remote.GitHubRemoteSource
import alex.zhurkov.git_repo_viewer.data.remote.GitHubRepositoryImpl
import alex.zhurkov.git_repo_viewer.data.remote.model.GitHubRepoResponse
import alex.zhurkov.git_repo_viewer.data.source.ConfigSourceImpl
import alex.zhurkov.git_repo_viewer.data.source.GitHubLocalSource
import alex.zhurkov.git_repo_viewer.data.source.GitHubLocalSourceImpl
import alex.zhurkov.git_repo_viewer.domain.config.ConfigSource
import alex.zhurkov.git_repo_viewer.domain.mapper.Mapper
import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import alex.zhurkov.git_repo_viewer.domain.model.Owner
import alex.zhurkov.git_repo_viewer.domain.repository.GitHubRepoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GitHubDataModule {

    @Provides
    fun gitHubRepository(
        configSource: ConfigSource,
        localSource: GitHubLocalSource,
        remoteSource: GitHubRemoteSource,
        repoRemoteMapper: Mapper<GitHubRepoResponse, GitHubRepo>
    ): GitHubRepoRepository = GitHubRepositoryImpl(
        configSource = configSource,
        localSource = localSource,
        remoteSource = remoteSource,
        repoRemoteMapper = repoRemoteMapper
    )

    @Provides
    @Singleton
    fun gitHubLocalSource(
        database: AppDatabase,
        repoMapper: EntityMapper<GitHubRepo, GitHubRepoWithInfoEntity>,
        configSource: ConfigSource
    ): GitHubLocalSource = GitHubLocalSourceImpl(
        database = database,
        repoMapper = repoMapper,
        configSource = configSource
    )

    @Provides
    @Singleton
    fun ownerMapper(): EntityMapper<Owner, OwnerEntity> = OwnerEntityMapper()

    @Provides
    @Singleton
    fun repoMapper(
        ownerMapper: EntityMapper<Owner, OwnerEntity>
    ): EntityMapper<GitHubRepo, GitHubRepoWithInfoEntity> = GitHubRepoEntityMapper(
        ownerMapper = ownerMapper
    )

    @Provides
    @Singleton
    fun configSource(): ConfigSource = ConfigSourceImpl()

    @Provides
    @Singleton
    fun repoResponseMapper(): Mapper<GitHubRepoResponse, GitHubRepo> = GitHubRepoResponseMapper()

}
