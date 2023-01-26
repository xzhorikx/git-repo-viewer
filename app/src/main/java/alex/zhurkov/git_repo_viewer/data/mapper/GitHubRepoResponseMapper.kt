package alex.zhurkov.git_repo_viewer.data.mapper

import alex.zhurkov.git_repo_viewer.data.remote.model.GitHubPageResponse
import alex.zhurkov.git_repo_viewer.domain.mapper.Mapper
import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo
import alex.zhurkov.git_repo_viewer.domain.model.Owner

class GitHubRepoResponseMapper : Mapper<GitHubPageResponse, GitHubRepo> {
    override fun map(from: GitHubPageResponse): GitHubRepo = GitHubRepo(
        id = from.id,
        owner = Owner(
            id = from.owner.id,
            login = from.owner.login,
            url = from.owner.url,
            avatar = from.owner.avatar
        ),
        name = from.name,
        url = from.url,
        createdAt = from.createdAt,
        updatedAt = from.updatedAt,
        description = from.description.orEmpty(),
        stars = from.stars
    )
}
