@file:OptIn(ExperimentalMaterialApi::class)

package alex.zhurkov.git_repo_viewer.feature.main

import alex.zhurkov.git_repo_viewer.R
import alex.zhurkov.git_repo_viewer.common.arch.UIEvent
import alex.zhurkov.git_repo_viewer.feature.main.di.MainActivityComponent
import alex.zhurkov.git_repo_viewer.feature.main.presentation.MainActivityAction
import alex.zhurkov.git_repo_viewer.feature.main.presentation.MainActivityEvent
import alex.zhurkov.git_repo_viewer.feature.main.presentation.MainActivityViewModel
import alex.zhurkov.git_repo_viewer.feature.main.presentation.MainActivityViewModelFactory
import alex.zhurkov.git_repo_viewer.feature.main.views.MainScreen
import alex.zhurkov.git_repo_viewer.feature.repoinfo.RepoInfoActivity
import alex.zhurkov.git_repo_viewer.feature.repoinfo.model.RepoInfoInputData
import alex.zhurkov.git_repo_viewer.feature.repoinfo.model.toBundle
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val component: MainActivityComponent by lazy {
        (application as MainActivityComponent.ComponentProvider).provideMainComponent(this)
    }

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory
    private val viewModel by viewModels<MainActivityViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        viewModel.observableEvents.observe(this, Observer(::renderEvent))
        setContent {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                uiModel = viewModel.observableModel,
                onPullToRefresh = { viewModel.dispatch(MainActivityAction.Refresh) },
                onLastItemVisible = {
                    viewModel.dispatch(MainActivityAction.LastVisibleItemChanged(id = it))
                },
                onClick = {
                    this@MainActivity.startActivity(
                        Intent(this@MainActivity, RepoInfoActivity::class.java).putExtras(
                            RepoInfoInputData(repoId = it.id.toLong()).toBundle()
                        )
                    )
                },
                onFilterSelected = { viewModel.dispatch(MainActivityAction.FilterSelected(it)) },
                onFavoriteClick = { viewModel.dispatch(MainActivityAction.FavoriteClicked(it)) },
            )
        }
    }

    private fun renderEvent(event: UIEvent) {
        if (event is MainActivityEvent) {
            when (event) {
                is MainActivityEvent.DisplayError -> {
                    Toast.makeText(
                        this,
                        getString(R.string.error_message_template, event.e),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is MainActivityEvent.NetworkConnectionChanged -> {
                    @StringRes val textRes = when (event.isConnected) {
                        true -> R.string.network_restored
                        false -> R.string.error_network_disconnected
                    }
                    Toast.makeText(this, getString(textRes), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}