@file:OptIn(ExperimentalMaterial3Api::class)

package alex.zhurkov.git_repo_viewer.feature.repoinfo.views

import alex.zhurkov.git_repo_viewer.R
import alex.zhurkov.git_repo_viewer.feature.main.model.GitHubRepoItem
import alex.zhurkov.git_repo_viewer.feature.main.views.RepositoryDetail
import alex.zhurkov.git_repo_viewer.feature.main.views.RepositoryLoading
import alex.zhurkov.git_repo_viewer.feature.main.views.RepositoryPreview
import alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation.RepoInfoActivityModel
import alex.zhurkov.git_repo_viewer.ui.theme.GitRepoViewerTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData

@Composable
fun RepoInfoScreen(
    modifier: Modifier = Modifier,
    uiModel: LiveData<RepoInfoActivityModel>,
    onBackClick: () -> Unit,
    onUrlClick: (GitHubRepoItem.Data) -> Unit,
    onFavoriteClick: (GitHubRepoItem.Data) -> Unit
) {
    val model by uiModel.observeAsState()
    model?.let { renderModel ->
        GitRepoViewerTheme {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(stringResource(id = R.string.repo_info_title))
                        },
                        navigationIcon = {
                            IconButton(onClick = { onBackClick() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back arrow"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                RepoInfoContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            PaddingValues(
                                start = dimensionResource(id = R.dimen.padding_16),
                                end = dimensionResource(id = R.dimen.padding_16),
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding()
                            )
                        ),
                    renderModel = renderModel,
                    onUrlClick = onUrlClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }

    }

}

@Composable
fun RepoInfoContent(
    modifier: Modifier = Modifier,
    renderModel: RepoInfoActivityModel,
    onUrlClick: (GitHubRepoItem.Data) -> Unit,
    onFavoriteClick: (GitHubRepoItem.Data) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_4))
    ) {
        when (val item = renderModel.item) {
            is GitHubRepoItem.Data -> {
                RepositoryPreview(
                    item,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /* no need to handle */ },
                    onFavoriteClick = onFavoriteClick
                )
                RepositoryDetail(
                    iconRes = R.drawable.ic_forks,
                    title = stringResource(id = R.string.forks),
                    body = renderModel.forks.toString()
                )
                RepositoryDetail(
                    iconRes = R.drawable.ic_language,
                    title = renderModel.language?.let {
                        stringResource(id = R.string.language_template, it)
                    } ?: stringResource(id = R.string.error_language_null),
                    body = null
                )
                RepositoryDetail(
                    iconRes = R.drawable.ic_calendar_created,
                    title = stringResource(
                        id = R.string.created_at_template,
                        renderModel.createdAt
                    ),
                    body = null
                )
                RepositoryDetail(
                    modifier = Modifier.clickable { onUrlClick(item) },
                    iconRes = R.drawable.ic_link,
                    title = renderModel.url,
                    body = null,
                )
            }
            is GitHubRepoItem.Loading -> RepositoryLoading(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.shimmer_height))
            )
        }
    }
}