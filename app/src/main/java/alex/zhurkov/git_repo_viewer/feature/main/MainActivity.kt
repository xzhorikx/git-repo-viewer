@file:OptIn(ExperimentalMaterialApi::class)

package alex.zhurkov.git_repo_viewer.feature.main

import alex.zhurkov.git_repo_viewer.R
import alex.zhurkov.git_repo_viewer.feature.main.di.MainActivityComponent
import alex.zhurkov.git_repo_viewer.feature.main.presentation.MainActivityViewModel
import alex.zhurkov.git_repo_viewer.feature.main.presentation.MainActivityViewModelFactory
import alex.zhurkov.git_repo_viewer.feature.main.views.MainScreen
import alex.zhurkov.git_repo_viewer.ui.theme.GitrepoviewerTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val component: MainActivityComponent by lazy {
        (application as MainActivityComponent.ComponentProvider).provideMainComponent(this)
    }

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory
    private val viewModel by viewModels<MainActivityViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val scrollBehavior =
                TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            GitrepoviewerTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(id = R.string.app_bar_title)) },
                            scrollBehavior = scrollBehavior
                        )
                    }
                ) { paddingValues ->
                    MainScreen(
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
                        uiModel = viewModel.observableModel,
                        onPullToRefresh = {},
                        onLastItemVisible = {},
                        onClick = {}
                    )
                }
            }
        }
    }
}