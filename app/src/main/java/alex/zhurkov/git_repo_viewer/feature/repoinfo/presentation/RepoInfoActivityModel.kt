package alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIModel
import alex.zhurkov.git_repo_viewer.feature.main.model.GitHubRepoItem

data class RepoInfoActivityModel(
    val item: GitHubRepoItem,
    val forks: Int,
    val language: String?,
    val createdAt: String,
    val url: String
) : UIModel