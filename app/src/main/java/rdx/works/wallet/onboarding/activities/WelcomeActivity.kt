package rdx.works.wallet.onboarding.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import org.koin.core.parameter.parametersOf
import rdx.works.wallet.R
import rdx.works.wallet.core.RadixActivity
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.UiEvent
import rdx.works.wallet.core.mvvm.register
import rdx.works.wallet.core.rx.click
import rdx.works.wallet.databinding.ActivityWelcomeBinding
import rdx.works.wallet.onboarding.actions.GoToTermsOfServiceAction
import rdx.works.wallet.onboarding.presenters.WelcomePresenter

class WelcomeActivity : RadixActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun getContentView() = R.layout.activity_welcome

    private val presenter: WelcomePresenter by scope.inject {
        parametersOf(this@WelcomeActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.bind(findViewById(R.id.rootView))!!

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }

        setTitle(R.string.welcome_title)
    }

    override fun collectViewUiEventsGenerators(): Array<Observable<out UiEvent>> = arrayOf(
        binding.continueButton.click()
    )

    private fun handlePresenterAction(action: PresenterAction) {
        when (action) {
            is GoToTermsOfServiceAction -> TermsOfServiceActivity.launch(this)
        }
    }

    companion object {

        fun launch(activity: Activity) = activity.startActivity(
            Intent(activity, WelcomeActivity::class.java)
        )
    }
}
