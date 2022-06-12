package rdx.works.wallet.onboarding.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import org.koin.core.parameter.parametersOf
import rdx.works.wallet.R
import rdx.works.wallet.core.RadixActivity
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.UiEvent
import rdx.works.wallet.core.mvvm.register
import rdx.works.wallet.core.rx.changeEvents
import rdx.works.wallet.core.rx.click
import rdx.works.wallet.databinding.ActivityConfirmPinBinding
import rdx.works.wallet.onboarding.actions.GoToCreatingAccountAction
import rdx.works.wallet.onboarding.presenters.ConfirmPinPresenter
import rdx.works.wallet.onboarding.viewmodels.ConfirmPinViewModel

class ConfirmPinActivity : RadixActivity() {

    private lateinit var binding: ActivityConfirmPinBinding

    private val presenter: ConfirmPinPresenter by scope.inject {
        parametersOf(this@ConfirmPinActivity)
    }

    private val viewModel: ConfirmPinViewModel by scope.inject()

    override fun getContentView() = R.layout.activity_confirm_pin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animateOpeningTransition()

        setTitle(R.string.confirm_pin_title)

        binding = DataBindingUtil.bind(findViewById(R.id.rootView))!!
        binding.model = viewModel

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                animateClosingTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun collectViewUiEventsGenerators(): Array<Observable<out UiEvent>> = arrayOf(
        binding.pin.changeEvents(),
        binding.continueButton.click()
    )

    override fun onBackPressed() {
        super.onBackPressed()
        animateClosingTransition()
    }

    private fun handlePresenterAction(action: PresenterAction) {
        when (action) {
            is GoToCreatingAccountAction -> CreatingAccountActivity.launch(this)
        }
    }

    companion object {

        fun launch(activity: Activity) = activity.startActivity(
            Intent(activity, ConfirmPinActivity::class.java)
        )
    }
}
