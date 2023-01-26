package alex.zhurkov.git_repo_viewer.domain.mapper

interface Mapper<T, E> {
    fun map(from: T): E
}
