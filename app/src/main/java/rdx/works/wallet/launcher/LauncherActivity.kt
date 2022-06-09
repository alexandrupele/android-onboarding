package rdx.works.wallet.launcher

import android.os.Bundle
import org.koin.android.ext.android.inject
import rdx.works.wallet.R
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.RadixActivity
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.register
import rdx.works.wallet.launcher.actions.GoToDashboardAction
import rdx.works.wallet.launcher.actions.GoToOnboardingAction
import rdx.works.wallet.onboarding.activities.PersonalInformationActivity

class LauncherActivity : RadixActivity() {

    private val logger by lazy { Logger("LauncherActivity") }

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
            is GoToDashboardAction -> logger.debug("going to dashboard")
            is GoToOnboardingAction -> PersonalInformationActivity.launch(this)

            else -> Unit
        }
    }
}
