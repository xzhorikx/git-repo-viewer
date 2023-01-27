@file:OptIn(ExperimentalMaterialApi::class)

package alex.zhurkov.git_repo_viewer.feature.repoinfo

import alex.zhurkov.git_repo_viewer.common.arch.UIEvent
import alex.zhurkov.git_repo_viewer.feature.repoinfo.di.RepoInfoActivityComponent
import alex.zhurkov.git_repo_viewer.feature.repoinfo.model.getInputData
import alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation.RepoInfoActivityAction
import alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation.RepoInfoActivityEvent
import alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation.RepoInfoActivityViewModel
import alex.zhurkov.git_repo_viewer.feature.repoinfo.presentation.RepoInfoActivityViewModelFactory
import alex.zhurkov.git_repo_viewer.feature.repoinfo.views.RepoInfoScreen
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Observer
import javax.inject.Inject

class RepoInfoActivity : ComponentActivity() {

    private val component: RepoInfoActivityComponent by lazy {
        (application as RepoInfoActivityComponent.ComponentProvider).provideMainComponent(
            activity = this,
            inputData = intent.extras.getInputData()
        )
    }

    @Inject
    lateinit var viewModelFactory: RepoInfoActivityViewModelFactory
    private val viewModel by viewModels<RepoInfoActivityViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        viewModel.observableEvents.observe(this, Observer(::renderEvent))
        setContent {
            val context = LocalContext.current
            RepoInfoScreen(
                modifier = Modifier.fillMaxSize(),
                uiModel = viewModel.observableModel,
                onBackClick = { this@RepoInfoActivity.finish() },
                onUrlClick = {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                    )
                },
                onFavoriteClick = { viewModel.dispatch(RepoInfoActivityAction.FavoriteClicked(it)) },
            )
        }
    }


    private fun renderEvent(event: UIEvent) {
        if (event is RepoInfoActivityEvent) {
            // Handle any events here
        }
    }
}