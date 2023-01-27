package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.Reducer
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage

class MainActivityReducer : Reducer<MainActivityState, MainActivityChange> {
    override fun reduce(state: MainActivityState, change: MainActivityChange): MainActivityState {
        return when (change) {
            is MainActivityChange.PageLoaded -> when (change.data) {
                null -> {
                    // When new page is null, it's an indicator that all pages were loaded
                    val lastPage = state.pages.lastOrNull() ?: GitHubReposPage(
                        pageId = state.nextPage ?: state.initialPageId,
                        isLastPage = true,
                        repoFilter = state.repoFilter,
                        repos = emptyList()
                    )
                    state.copy(pages = state.pages + lastPage.copy(isLastPage = true))
                }
                else -> state.copy(pages = state.pages + change.data)
            }
            is MainActivityChange.PageLoadingChanged -> state.copy(isPageLoading = change.isLoading)
            is MainActivityChange.RefreshChanged -> state.copy(isRefreshing = change.isRefreshing)
            is MainActivityChange.FilterChanged -> {
                state.copy(repoFilter = change.data, pages = emptyList())
            }
            MainActivityChange.ItemsCleared -> state.copy(pages = emptyList())
            is MainActivityChange.LastVisibleItemChanged -> state.copy(lastVisibleItemId = change.id)
            is MainActivityChange.FavoritesChanged -> state.copy(favorites = change.data)
        }
    }
}
