package alex.zhurkov.git_repo_viewer.data.database.mapper

import alex.zhurkov.git_repo_viewer.data.database.entity.OwnerEntity
import alex.zhurkov.git_repo_viewer.domain.model.Owner

class OwnerEntityMapper : EntityMapper<Owner, OwnerEntity> {
    override fun toModel(entity: OwnerEntity): Owner {
        return Owner(
            id = entity.id,
            login = entity.login,
            url = entity.url,
            avatar = entity.avatar
        )
    }

    override fun toEntity(model: Owner): OwnerEntity {
        return OwnerEntity(
            id = model.id,
            login = model.login,
            url = model.url,
            avatar = model.avatar
        )
    }
}
