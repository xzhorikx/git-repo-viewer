package alex.zhurkov.git_repo_viewer.data.source

import alex.zhurkov.git_repo_viewer.BuildConfig
import alex.zhurkov.git_repo_viewer.domain.config.ConfigSource

class ConfigSourceImpl : ConfigSource {
    override val pageSize: Int
        get() = BuildConfig.PAGE_SIZE
    override val callTimeOutSec: Long
        get() = BuildConfig.CALL_TIME_OUT_SEC
    override val cacheStaleSec: Long
        get() = BuildConfig.CACHE_STALE_SEC
    override val cacheSizeByte: Long
        get() = BuildConfig.CACHE_SIZE_BYTE
}
