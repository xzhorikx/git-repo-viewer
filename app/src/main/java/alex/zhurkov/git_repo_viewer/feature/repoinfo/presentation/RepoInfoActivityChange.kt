package alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIStateChange
import alex.zhurkov.git_repo_viewer.domain.model.GitHubRepo

sealed class RepoInfoActivityChange : UIStateChange {
    data class RepoChanged(val data: GitHubRepo) : RepoInfoActivityChange()
    data class FavoritesChanged(val data: List<Long>) :  RepoInfoActivityChange()
}