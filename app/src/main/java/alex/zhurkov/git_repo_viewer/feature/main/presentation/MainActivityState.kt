package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIState
import alex.zhurkov.git_repo_viewer.common.whenFalse
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter

data class MainActivityState(
    val pages: List<GitHubReposPage>,
    val isPageLoading: Boolean,
    val isRefreshing: Boolean,
    val repoFilter: RepoFilter,
    val lastVisibleItemId: Long?
) : UIState {

    companion object {
        val EMPTY = MainActivityState(
            pages = emptyList(),
            isPageLoading = false,
            repoFilter = RepoFilter.TimeFrame.LastDay,
            isRefreshing = false,
            lastVisibleItemId = null
        )
    }

    fun isPageLoaded(pageId: Int): Boolean = pages.any { it.pageId == pageId }

    val isLastPageLoaded = pages.any(GitHubReposPage::isLastPage)

    val initialPageId = 1

    val nextPage =
        isLastPageLoaded.whenFalse { (pages.lastOrNull()?.pageId?.inc() ?: initialPageId) }

    val lastRepoId = pages.flatMap { it.repos }.lastOrNull()?.id

}
