package alex.zhurkov.git_repo_viewer.domain.model

import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

enum class RepoTimeframe {
    LastDay, LastWeek, LastMonth
}

private const val DATE_FORMAT = "yyyy-MM-dd"

fun RepoTimeframe.asIsoDateFromNow(): String {
    val current = DateTime().minusDays(1)
    val date = when (this) {
        RepoTimeframe.LastDay -> current.minusDays(1)
        RepoTimeframe.LastWeek -> current.minusWeeks(1)
        RepoTimeframe.LastMonth -> current.minusMonths(1)
    }
    return SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)
}
