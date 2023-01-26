@file:OptIn(ExperimentalTextApi::class)

package alex.zhurkov.git_repo_viewer.feature.main.views

import alex.zhurkov.git_repo_viewer.R
import alex.zhurkov.git_repo_viewer.feature.main.model.GitHubRepoItem
import alex.zhurkov.git_repo_viewer.feature.main.presentation.MainActivityModel
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.LiveData
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    uiModel: LiveData<MainActivityModel>,
    onPullToRefresh: () -> Unit,
    onLastItemVisible: (id: String) -> Unit,
    onClick: (GitHubRepoItem.Data) -> Unit
) {
    val model by uiModel.observeAsState()
    model?.let { renderModel ->
        val pullToRefreshState =
            rememberPullRefreshState(renderModel.isRefreshing, onPullToRefresh)
        val state = rememberLazyListState()
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            Box(Modifier.pullRefresh(pullToRefreshState)) {
                LazyColumn(
                    modifier = modifier.testTag("item_container"),
                    state = state,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_8)),
                    contentPadding = PaddingValues(
                        vertical = dimensionResource(id = R.dimen.padding_8)
                    )
                ) {
                    itemsIndexed(
                        items = renderModel.items,
                        key = { _, item -> item.id }) { index, item ->
                        val lastVisibleId by remember {
                            derivedStateOf {
                                with(state.layoutInfo) {
                                    (visibleItemsInfo.lastOrNull()?.key as? String).takeIf { it == (item as? GitHubRepoItem.Data)?.id }
                                }
                            }
                        }
                        lastVisibleId?.run(onLastItemVisible)
                        when (item) {
                            is GitHubRepoItem.Data -> {
                                RepositoryPreview(
                                    item,
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = onClick
                                )
                            }
                            is GitHubRepoItem.Loading -> {
                                RepositoryLoading(
                                    item = item,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(dimensionResource(id = R.dimen.shimmer_height))
                                )
                            }
                        }
                        if (index < renderModel.items.lastIndex) {
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_8)))
                            Divider(
                                color = MaterialTheme.colorScheme.outline,
                                thickness = dimensionResource(id = R.dimen.divider_thickness)
                            )
                        }
                    }
                }
                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = renderModel.isRefreshing,
                    state = pullToRefreshState,
                )
            }
        }
    }
}

@Composable
fun RepositoryPreview(
    item: GitHubRepoItem.Data,
    modifier: Modifier = Modifier,
    onClick: (GitHubRepoItem.Data) -> Unit
) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .placeholder(R.drawable.ic_loading_animated)
            .error(R.drawable.ic_avatar)
            .build()
    }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(data = item.owner.avatar)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader,
        error = ColorPainter(Color.Black),
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick(item) },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                val title = buildAnnotatedString {
                    append(item.owner.login)
                    append("/")
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ) {
                        append(item.name)
                    }
                }
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    style = MaterialTheme.typography.titleMedium.copy(hyphens = Hyphens.Auto),
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_16)))
                Text(
                    text = item.description ?: stringResource(id = R.string.repo_no_description),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_16)))
            Image(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.avatar_size))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corners_4))),
                painter = painter,
                contentDescription = "Contributor avatar",
                contentScale = ContentScale.Crop,
            )
        } // End of repo title row
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_16)))
        Row {
            RepositoryDetail(
                iconRes = R.drawable.ic_baseline_star_border_24,
                title = item.stars.toString(),
                body = stringResource(
                    id = R.string.stars
                )
            )
        }
    }
}

@Composable
fun RepositoryLoading(
    modifier: Modifier = Modifier,
    item: GitHubRepoItem.Loading
) {
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.Window,
        theme = LocalShimmerTheme.current.copy(
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 600,
                    easing = LinearEasing,
                    delayMillis = 300,
                ),
                repeatMode = RepeatMode.Restart,
            ),
        ),
    )
    Box(
        modifier = modifier
            .shimmer(shimmer)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .testTag(item.id)
    )
}

@Composable
fun RepositoryDetail(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    title: String,
    body: String
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_repo_detail_size)),
            painter = painterResource(id = iconRes),
            contentDescription = body,
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_8)))
        Column {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_4)))
            Text(
                text = body,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}