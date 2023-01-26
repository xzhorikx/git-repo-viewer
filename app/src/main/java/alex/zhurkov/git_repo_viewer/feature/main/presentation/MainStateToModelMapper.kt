package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.StateToModelMapper
import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.feature.main.model.GitHubRepoItem
import alex.zhurkov.git_repo_viewer.feature.main.model.toItem

class MainStateToModelMapper : StateToModelMapper<MainActivityState, MainActivityModel> {
    override fun mapStateToModel(state: MainActivityState): MainActivityModel {
        val items = mapItems(pages = state.pages, isPageLoading = state.isPageLoading)
        return MainActivityModel(
            items = items,
        )
    }

    private fun mapItems(
        pages: List<GitHubReposPage>,
        isPageLoading: Boolean
    ): List<GitHubRepoItem> {
        val items = pages.flatMap { page ->
            page.repos.map { repo -> repo.toItem() }
        }
        val loadingIndicators = when (isPageLoading) {
            true -> (0..7).map { GitHubRepoItem.Loading(id = "loading_$it") }
            false -> emptyList()
        }
        return (items + loadingIndicators).distinctBy { it.id }
    }
}