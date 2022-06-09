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
import rdx.works.wallet.core.rx.textChangeEvents
import rdx.works.wallet.databinding.ActivityPersonalInformationBinding
import rdx.works.wallet.onboarding.presenters.PersonalInformationPresenter
import rdx.works.wallet.onboarding.viewmodels.PersonalInformationViewModel

class PersonalInformationActivity : RadixActivity() {

    private lateinit var binding: ActivityPersonalInformationBinding

    private val presenter: PersonalInformationPresenter by scope.inject {
        parametersOf(this@PersonalInformationActivity)
    }

    private val viewModel: PersonalInformationViewModel by scope.inject()

    override fun getContentView() = R.layout.activity_personal_information

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.bind(findViewById(R.id.rootView))!!
        binding.model = viewModel

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }
    }

    override fun collectViewUiEventsGenerators(): Array<Observable<out UiEvent>> = arrayOf(
        binding.firstName.textChangeEvents(),
        binding.lastName.textChangeEvents(),
        binding.phoneNumber.textChangeEvents()
    )

    private fun handlePresenterAction(action: PresenterAction) {

    }

    companion object {

        fun launch(activity: Activity) = activity.startActivity(
            Intent(activity, PersonalInformationActivity::class.java)
        )
    }
}
