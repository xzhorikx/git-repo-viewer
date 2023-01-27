package alex.zhurkov.git_repo_viewer.feature.repoinfo.model

import android.os.Bundle

data class RepoInfoInputData(
    val repoId: Long
)

private const val EXTRA_REPO_ID = "EXTRA_REPO_ID"

internal fun RepoInfoInputData.toBundle() = Bundle().apply {
    putLong(EXTRA_REPO_ID, repoId)
}

internal fun Bundle?.getInputData() = RepoInfoInputData(
    repoId = this?.getLong(EXTRA_REPO_ID, 0) ?: 0
)
