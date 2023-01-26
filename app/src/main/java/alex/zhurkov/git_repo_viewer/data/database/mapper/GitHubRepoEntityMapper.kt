package alex.zhurkov.git_repo_viewer.data.database.mapper

import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.GitHubRepoWithInfoEntity
import alex.zhurkov.git_repo_viewer.data.database.entity.OwnerEntity
import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import alex.zhurkov.git_repo_viewer.domain.model.Owner

class GitHubRepoEntityMapper(
    private val ownerMapper: EntityMapper<Owner, OwnerEntity>
) : EntityMapper<GitHubRepo, GitHubRepoWithInfoEntity> {
    override fun toModel(entity: GitHubRepoWithInfoEntity): GitHubRepo {
        return GitHubRepo(
            id = entity.gitHubRepoEntity.repoId,
            owner = ownerMapper.toModel(entity.owner),
            name = entity.gitHubRepoEntity.name,
            url = entity.gitHubRepoEntity.url,
            description = entity.gitHubRepoEntity.description,
            createdAt = entity.gitHubRepoEntity.createdAt,
            updatedAt = entity.gitHubRepoEntity.updatedAt,
            stars = entity.gitHubRepoEntity.stars
        )
    }

    override fun toEntity(model: GitHubRepo): GitHubRepoWithInfoEntity {
        return GitHubRepoWithInfoEntity(
            owner = ownerMapper.toEntity(model.owner),
            gitHubRepoEntity = GitHubRepoEntity(
                repoId = model.id,
                ownerId = model.owner.id,
                name = model.name,
                url = model.url,
                description = model.description,
                createdAt = model.createdAt,
                updatedAt = model.updatedAt,
                stars = model.stars
            )
        )
    }
}
