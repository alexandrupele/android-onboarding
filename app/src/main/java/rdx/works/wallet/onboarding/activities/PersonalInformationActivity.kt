package rdx.works.wallet.onboarding.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import io.reactivex.Observable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import rdx.works.wallet.R
import rdx.works.wallet.core.RadixActivity
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.UiEvent
import rdx.works.wallet.core.mvvm.register
import rdx.works.wallet.core.rx.afterTextChangeEvents
import rdx.works.wallet.databinding.ActivityPersonalInformationBinding
import rdx.works.wallet.onboarding.presenters.PersonalInformationPresenter

class PersonalInformationActivity : RadixActivity() {

    private lateinit var binding: ActivityPersonalInformationBinding

    private val presenter: PersonalInformationPresenter by inject {
        parametersOf(this@PersonalInformationActivity)
    }

    override fun getContentView() = R.layout.activity_personal_information

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }
    }

    override fun collectViewUiEventsGenerators(): Array<Observable<out UiEvent>> = arrayOf(
        binding.firstName.afterTextChangeEvents(),
        binding.lastName.afterTextChangeEvents(),
        binding.phoneNumber.afterTextChangeEvents(),
    )

    private fun handlePresenterAction(action: PresenterAction) {

    }

    companion object {

        fun launch(activity: Activity) = activity.startActivity(
            Intent(activity, PersonalInformationActivity::class.java)
        )
    }
}
