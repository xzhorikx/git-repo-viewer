package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.common.arch.UIEvent

sealed class MainActivityEvent : UIEvent {
    data class DisplayError(val e: Throwable) : MainActivityEvent()
    data class NetworkConnectionChanged(val isConnected: Boolean) : MainActivityEvent()
}