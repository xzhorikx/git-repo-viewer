package alex.zhurkov.git_repo_viewer.domain.di

import alex.zhurkov.git_repo_viewer.app.di.ActivityScope
import alex.zhurkov.git_repo_viewer.app.di.AppContext
import alex.zhurkov.git_repo_viewer.data.usecase.NetworkConnectionUseCaseImpl
import alex.zhurkov.git_repo_viewer.domain.usecase.NetworkConnectionUseCase
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class NetworkConnectionModule {
    @Provides
    @ActivityScope
    fun networkConnectionUseCase(
        @AppContext context: Context
    ): NetworkConnectionUseCase = NetworkConnectionUseCaseImpl(
        context = context
    )
}
