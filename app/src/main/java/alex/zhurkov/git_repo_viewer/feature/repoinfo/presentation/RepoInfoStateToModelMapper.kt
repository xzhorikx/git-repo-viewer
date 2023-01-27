package alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation

import alex.zhurkov.git_repo_viewer.common.arch.StateToModelMapper
import alex.zhurkov.git_repo_viewer.feature.main.model.GitHubRepoItem
import alex.zhurkov.git_repo_viewer.feature.main.model.toItem

class RepoInfoStateToModelMapper :
    StateToModelMapper<RepoInfoActivityState, RepoInfoActivityModel> {
    override fun mapStateToModel(state: RepoInfoActivityState): RepoInfoActivityModel {
        val item = when (val repo = state.repo) {
            null -> GitHubRepoItem.Loading(id = "loading")
            else -> repo.toItem(isFavorite = repo.id in state.favorites)
        }
        return RepoInfoActivityModel(
            item = item,
            forks = state.repo?.forks ?: 0,
            language = state.repo?.language.takeIf { it?.isNotEmpty() == true },
            createdAt = state.repo?.createdAt ?: "",
            url = state.repo?.url ?: "",
        )
    }
}