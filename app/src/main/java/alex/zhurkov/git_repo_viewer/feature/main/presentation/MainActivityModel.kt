package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIModel
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import alex.zhurkov.git_repo_viewer.feature.main.model.GitHubRepoItem

data class MainActivityModel(
    val items: List<GitHubRepoItem>,
    val selectedFilter: RepoFilter,
    val isRefreshing: Boolean
) : UIModel