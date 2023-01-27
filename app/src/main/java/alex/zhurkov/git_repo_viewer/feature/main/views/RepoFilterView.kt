package alex.zhurkov.git_repo_viewer.feature.main.views

import alex.zhurkov.git_repo_viewer.R
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun RepoFilterDialog(
    modifier: Modifier = Modifier,
    selectedFilter: RepoFilter,
    onFilterClick: (RepoFilter) -> Unit
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_8)),
    ) {
        Text(
            stringResource(id = R.string.title_filter_selection),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_16)))
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_8)),
        ) {
            RepoFilterSelection(
                modifier = modifier.fillMaxWidth(),
                repoFilter = RepoFilter.Favorites,
                isSelected = selectedFilter == RepoFilter.Favorites,
                onFilterClick = onFilterClick
            )
            RepoFilterSelection(
                modifier = modifier.fillMaxWidth(),
                repoFilter = RepoFilter.TimeFrame.LastDay,
                isSelected = selectedFilter == RepoFilter.TimeFrame.LastDay,
                onFilterClick = onFilterClick
            )
            RepoFilterSelection(
                modifier = modifier.fillMaxWidth(),
                repoFilter = RepoFilter.TimeFrame.LastWeek,
                isSelected = selectedFilter == RepoFilter.TimeFrame.LastWeek,
                onFilterClick = onFilterClick
            )
            RepoFilterSelection(
                modifier = modifier.fillMaxWidth(),
                repoFilter = RepoFilter.TimeFrame.LastMonth,
                isSelected = selectedFilter == RepoFilter.TimeFrame.LastMonth,
                onFilterClick = onFilterClick
            )
        }
    }
}

@Composable
fun RepoFilterSelection(
    modifier: Modifier = Modifier,
    repoFilter: RepoFilter,
    isSelected: Boolean,
    onFilterClick: (RepoFilter) -> Unit
) {
    val textStyle = when (isSelected) {
        true -> MaterialTheme.typography.titleMedium
        false -> MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400)
    }
    val iconTintAlpha = when (isSelected) {
        true -> 1.0f
        false -> 0.7f
    }
    Row(
        modifier = modifier.clickable { onFilterClick(repoFilter) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_repo_detail_size)),
            painter = painterResource(id = repoFilter.mapIconRes()),
            contentDescription = "$modifier",
            tint = LocalContentColor.current.copy(alpha = iconTintAlpha)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_16)))
        Text(
            stringResource(id = repoFilter.mapStringRes()),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = textStyle
        )
    }
}

private fun RepoFilter.mapIconRes(): Int = when (this) {
    RepoFilter.Favorites -> R.drawable.ic_favorite
    RepoFilter.TimeFrame.LastDay -> R.drawable.ic_calendar_today
    RepoFilter.TimeFrame.LastMonth -> R.drawable.ic_calendar_month
    RepoFilter.TimeFrame.LastWeek -> R.drawable.ic_calendar_week
}