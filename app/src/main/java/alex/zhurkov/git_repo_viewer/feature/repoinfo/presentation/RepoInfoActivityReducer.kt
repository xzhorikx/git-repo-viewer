package alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation

import alex.zhurkov.git_repo_viewer.common.arch.Reducer

class RepoInfoActivityReducer : Reducer<RepoInfoActivityState, RepoInfoActivityChange> {
    override fun reduce(
        state: RepoInfoActivityState,
        change: RepoInfoActivityChange
    ): RepoInfoActivityState {
        return when (change) {
            is RepoInfoActivityChange.RepoChanged -> state.copy(repo = change.data)
            is RepoInfoActivityChange.FavoritesChanged -> state.copy(favorites = change.data)
        }
    }
}
