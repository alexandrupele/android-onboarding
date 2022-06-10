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
import rdx.works.wallet.databinding.ActivityCredentialsBinding
import rdx.works.wallet.onboarding.actions.GoToPersonalInformationAction
import rdx.works.wallet.onboarding.presenters.CredentialsPresenter
import rdx.works.wallet.onboarding.viewmodels.CredentialsViewModel

class CredentialsActivity : RadixActivity() {

    private lateinit var binding: ActivityCredentialsBinding

    private val presenter: CredentialsPresenter by scope.inject {
        parametersOf(this@CredentialsActivity)
    }

    private val viewModel: CredentialsViewModel by scope.inject()

    override fun getContentView() = R.layout.activity_credentials

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.bind(findViewById(R.id.rootView))!!
        binding.model = viewModel

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }

        setTitle(R.string.credentials_title)
    }

    override fun collectViewUiEventsGenerators(): Array<Observable<out UiEvent>> = arrayOf(
        binding.email.changeEvents(),
        binding.password.changeEvents(),
        binding.continueButton.click()
    )

    private fun handlePresenterAction(action: PresenterAction) {
        when (action) {
            is GoToPersonalInformationAction -> PersonalInformationActivity.launch(this)
        }
    }

    companion object {

        fun launch(activity: Activity) = activity.startActivity(
            Intent(activity, CredentialsActivity::class.java)
        )
    }
}
