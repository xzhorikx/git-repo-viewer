package alex.zhurkov.git_repo_viewer.data.database.mapper

interface EntityMapper<Model, Entity> {
    fun toModel(entity: Entity): Model
    fun toEntity(model: Model): Entity
}
