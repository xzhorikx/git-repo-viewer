package alex.zhurkov.git_repo_viewer.data.database.converter

import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import androidx.room.TypeConverter

class FilterConverter {

    @TypeConverter
    fun fromFilter(filter: RepoFilter): String = when (filter) {
        RepoFilter.Favorites -> "favorites"
        RepoFilter.TimeFrame.LastDay -> "last_day"
        RepoFilter.TimeFrame.LastMonth -> "last_month"
        RepoFilter.TimeFrame.LastWeek -> "last_week"
    }

    @TypeConverter
    fun toFilter(value: String): RepoFilter = when (value) {
        "favorites" -> RepoFilter.Favorites
        "last_day" -> RepoFilter.TimeFrame.LastDay
        "last_month" -> RepoFilter.TimeFrame.LastMonth
        "last_week" -> RepoFilter.TimeFrame.LastWeek
        else -> throw IllegalArgumentException("No filter class found for [$value]")
    }
}
