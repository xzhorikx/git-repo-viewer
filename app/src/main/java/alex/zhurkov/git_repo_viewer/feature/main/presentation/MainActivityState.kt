package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIState
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage

data class MainActivityState(
    val pages: List<GitHubReposPage>,
    val isPageLoading: Boolean
) : UIState {

    companion object {
        val EMPTY = MainActivityState(
            pages = emptyList(),
            isPageLoading = false
        )
    }
}
