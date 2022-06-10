package rdx.works.wallet.dashboard.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import org.koin.core.parameter.parametersOf
import rdx.works.wallet.R
import rdx.works.wallet.core.RadixActivity
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.register
import rdx.works.wallet.dashboard.events.SignOutEvent
import rdx.works.wallet.dashboard.presenters.DashboardPresenter
import rdx.works.wallet.dashboard.viewmodels.DashboardViewModel
import rdx.works.wallet.databinding.ActivityDashboardBinding
import rdx.works.wallet.launcher.actions.GoToOnboardingAction
import rdx.works.wallet.onboarding.activities.WelcomeActivity

class DashboardActivity : RadixActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val presenter: DashboardPresenter by scope.inject {
        parametersOf(this@DashboardActivity)
    }

    private val viewModel: DashboardViewModel by scope.inject()

    override fun getContentView() = R.layout.activity_dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.bind(findViewById(R.id.rootView))!!
        binding.model = viewModel

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }

        setTitle(R.string.dashboard_title)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.signOut -> {
                manuallyEmitUiEvent(SignOutEvent())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    private fun handlePresenterAction(action: PresenterAction) {
        when (action) {
            is GoToOnboardingAction -> {
                WelcomeActivity.launch(this)
                finish()
            }
        }
    }

    companion object {

        fun launch(activity: Activity, asNewTask: Boolean = true) = activity.startActivity(
            Intent(activity, DashboardActivity::class.java).apply {
                if (asNewTask) {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        )
    }
}
