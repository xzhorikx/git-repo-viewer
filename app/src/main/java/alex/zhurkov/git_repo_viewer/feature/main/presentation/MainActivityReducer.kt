package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.Reducer

class MainActivityReducer : Reducer<MainActivityState, MainActivityChange> {
    override fun reduce(state: MainActivityState, change: MainActivityChange) = MainActivityState(
        pages = emptyList(),
        isPageLoading = false
    )
}
