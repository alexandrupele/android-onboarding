package rdx.works.wallet.dashboard.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rdx.works.wallet.dashboard.activities.DashboardActivity
import rdx.works.wallet.dashboard.presenters.DashboardPresenter
import rdx.works.wallet.dashboard.utils.SignOutPerformer
import rdx.works.wallet.dashboard.viewmodels.DashboardViewModel

fun provideDashboardModule() = module {

    scope(named<DashboardActivity>()) {

        scoped {
            DashboardViewModel(
                resources = androidContext().resources
            )
        }

        scoped { (activity: DashboardActivity) ->
            DashboardPresenter(
                uiEvents = activity.uiEvents,
                signOutPerformer = SignOutPerformer(
                    sessionRepository = get(),
                    userRepository = get(),
                ),
                userRepository = get(),
                viewModel = get()
            )
        }
    }
}
