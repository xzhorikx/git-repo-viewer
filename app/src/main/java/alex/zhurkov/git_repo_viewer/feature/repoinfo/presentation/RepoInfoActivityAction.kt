package alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIAction
import alex.zhurkov.git_repo_viewer.feature.main.model.GitHubRepoItem

sealed class RepoInfoActivityAction : UIAction {
    data class FavoriteClicked(val data: GitHubRepoItem.Data) : RepoInfoActivityAction()
}