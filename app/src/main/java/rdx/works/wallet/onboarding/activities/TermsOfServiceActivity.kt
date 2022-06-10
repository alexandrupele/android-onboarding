package rdx.works.wallet.onboarding.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.NavUtils
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
import rdx.works.wallet.databinding.ActivityTermsOfServiceBinding
import rdx.works.wallet.onboarding.actions.GoToCredentialsAction
import rdx.works.wallet.onboarding.presenters.TermsOfServicePresenter
import rdx.works.wallet.onboarding.viewmodels.TermsOfServiceViewModel

class TermsOfServiceActivity : RadixActivity() {

    private lateinit var binding: ActivityTermsOfServiceBinding

    private val presenter: TermsOfServicePresenter by scope.inject {
        parametersOf(this@TermsOfServiceActivity)
    }

    private val viewModel: TermsOfServiceViewModel by scope.inject()

    override fun getContentView() = R.layout.activity_terms_of_service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.bind(findViewById(R.id.rootView))!!
        binding.model = viewModel

        with(presenter) {
            register(lifecycle)
            actions.subscribe(::handlePresenterAction)
        }

        setTitle(R.string.terms_of_service_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun collectViewUiEventsGenerators(): Array<Observable<out UiEvent>> = arrayOf(
        binding.checkBox.changeEvents(),
        binding.continueButton.click()
    )

    private fun handlePresenterAction(action: PresenterAction) {
        when(action) {
            is GoToCredentialsAction -> CredentialsActivity.launch(this)
        }
    }

    companion object {

        fun launch(activity: Activity) = activity.startActivity(
            Intent(activity, TermsOfServiceActivity::class.java)
        )
    }
}
