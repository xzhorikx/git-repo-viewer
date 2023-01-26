package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIStateChange
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage

sealed class MainActivityChange : UIStateChange {
    data class PageLoadingChanged(val isLoading: Boolean) : MainActivityChange()
    data class RefreshChanged(val isRefreshing: Boolean) : MainActivityChange()
    data class PageLoaded(val data: GitHubReposPage?) : MainActivityChange()
}