package alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIState
import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo

data class RepoInfoActivityState(
    val repo: GitHubRepo?,
    val favorites: List<Long>
) : UIState {

    companion object {
        val EMPTY = RepoInfoActivityState(
            repo = null,
            favorites = emptyList()
        )
    }
}
