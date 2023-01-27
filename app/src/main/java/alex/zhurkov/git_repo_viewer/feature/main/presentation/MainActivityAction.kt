package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIAction
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter

sealed class MainActivityAction : UIAction {
    object Refresh : MainActivityAction()
    data class FilterSelected(val data: RepoFilter) : MainActivityAction()
}