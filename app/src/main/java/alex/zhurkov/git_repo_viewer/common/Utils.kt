package alex.zhurkov.git_repo_viewer.common

fun <T> Boolean?.whenTrue(func: () -> T) = if (this == true) func() else null

fun <T> Boolean?.whenFalse(func: () -> T) = if (this == false) func() else null

fun <T> T.singleItemList() = listOf(this)