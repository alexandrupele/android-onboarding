package rdx.works.wallet.launcher.activities

import android.os.Bundle
import org.koin.android.ext.android.inject
import rdx.works.wallet.R
import rdx.works.wallet.core.RadixActivity
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.register
import rdx.works.wallet.dashboard.activities.DashboardActivity
import rdx.works.wallet.launcher.actions.GoToDashboardAction
import rdx.works.wallet.launcher.actions.GoToOnboardingAction
import rdx.works.wallet.launcher.presenters.LauncherPresenter
import rdx.works.wallet.onboarding.activities.WelcomeActivity

class LauncherActivity : RadixActivity() {

    private val presenter: LauncherPresenter by inject()

    override fun getContentView() = R.layout.activity_launcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }
    }

    private fun handlePresenterAction(action: PresenterAction) {
        when (action) {
            is GoToDashboardAction -> DashboardActivity.launch(this)
            is GoToOnboardingAction -> WelcomeActivity.launch(this)
        }

        finish()
    }
}
