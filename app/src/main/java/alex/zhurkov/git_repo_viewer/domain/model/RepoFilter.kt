package alex.zhurkov.git_repo_viewer.domain.model

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

sealed class RepoFilter {
    sealed class TimeFrame : RepoFilter() {
        object LastDay : TimeFrame()
        object LastWeek : TimeFrame()
        object LastMonth : TimeFrame()
    }

    object Favorites : RepoFilter()
}

private const val DATE_FORMAT = "yyyy-MM-dd"

fun RepoFilter.TimeFrame.asIsoDateFromNow(): String {
    val current = DateTime.now().minusDays(1)
    val date = when (this) {
        RepoFilter.TimeFrame.LastDay -> current.minusDays(1)
        RepoFilter.TimeFrame.LastMonth -> current.minusWeeks(1)
        RepoFilter.TimeFrame.LastWeek -> current.minusMonths(1)
    }
    return DateTimeFormat.forPattern(DATE_FORMAT).print(date)
}

