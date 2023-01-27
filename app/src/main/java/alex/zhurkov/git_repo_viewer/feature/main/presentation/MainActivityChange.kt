package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIStateChange
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter

sealed class MainActivityChange : UIStateChange {
    object ItemsCleared : MainActivityChange()

    data class PageLoadingChanged(val isLoading: Boolean) : MainActivityChange()
    data class RefreshChanged(val isRefreshing: Boolean) : MainActivityChange()
    data class PageLoaded(val data: GitHubReposPage?) : MainActivityChange()
    data class FilterChanged(val data: RepoFilter) : MainActivityChange()
    data class LastVisibleItemChanged(val id: Long) : MainActivityChange()
}