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
import rdx.works.wallet.core.rx.changeEvents
import rdx.works.wallet.core.rx.click
import rdx.works.wallet.databinding.ActivityPersonalInformationBinding
import rdx.works.wallet.databinding.ActivityPinBinding
import rdx.works.wallet.onboarding.actions.GoToConfirmPinAction
import rdx.works.wallet.onboarding.actions.GoToPinAction
import rdx.works.wallet.onboarding.presenters.PersonalInformationPresenter
import rdx.works.wallet.onboarding.presenters.PinPresenter
import rdx.works.wallet.onboarding.viewmodels.PersonalInformationViewModel
import rdx.works.wallet.onboarding.viewmodels.PinViewModel

class PinActivity : RadixActivity() {

    private lateinit var binding: ActivityPinBinding

    private val presenter: PinPresenter by scope.inject {
        parametersOf(this@PinActivity)
    }

    private val viewModel: PinViewModel by scope.inject()

    override fun getContentView() = R.layout.activity_pin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.bind(findViewById(R.id.rootView))!!
        binding.model = viewModel

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }

        setTitle(R.string.pin_title)
    }

    override fun collectViewUiEventsGenerators(): Array<Observable<out UiEvent>> = arrayOf(
        binding.pin.changeEvents(),
        binding.continueButton.click()
    )

    private fun handlePresenterAction(action: PresenterAction) {
        when(action) {
            is GoToConfirmPinAction -> ConfirmPinActivity.launch(this)
        }
    }

    companion object {

        fun launch(activity: Activity) = activity.startActivity(
            Intent(activity, PinActivity::class.java)
        )
    }
}
