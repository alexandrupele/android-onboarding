package rdx.works.wallet.onboarding.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import org.koin.android.ext.android.inject
import rdx.works.wallet.R
import rdx.works.wallet.core.RadixActivity
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.register
import rdx.works.wallet.dashboard.activities.DashboardActivity
import rdx.works.wallet.onboarding.actions.GoToDashboardAction
import rdx.works.wallet.onboarding.presenters.CreatingAccountPresenter

class CreatingAccountActivity : RadixActivity() {

    private val presenter: CreatingAccountPresenter by inject()

    override fun getContentView() = R.layout.activity_creating_account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }

        setTitle(R.string.creating_account_title)
    }

    private fun handlePresenterAction(action: PresenterAction) {
        when (action) {
            is GoToDashboardAction -> {
                DashboardActivity.launch(this)
                finish()
            }
        }
    }

    companion object {

        fun launch(activity: Activity) = activity.startActivity(
            Intent(activity, CreatingAccountActivity::class.java)
        )
    }
}
