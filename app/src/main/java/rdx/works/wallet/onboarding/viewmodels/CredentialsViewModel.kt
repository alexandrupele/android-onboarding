package rdx.works.wallet.onboarding.viewmodels

import android.content.res.Resources
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import rdx.works.wallet.R
import rdx.works.wallet.onboarding.model.OnboardingData

class CredentialsViewModel(private val resources: Resources) {

    val email = ObservableField<CharSequence>()
    val emailErrorText = ObservableField<CharSequence>()
    val emailErrorEnabled = ObservableBoolean()

    val password = ObservableField<CharSequence>()
    val continueButtonEnabled = ObservableBoolean()

    fun loadScreenState(data: OnboardingData) {
        data.email?.let { email.set(it) }
        data.password?.let { password.set(it) }
    }

    fun setEmail(email: CharSequence) {
        this.email.set(email)
        enableOrDisableContinueButton()
    }

    fun setEmailValidationError() {
        emailErrorText.set(resources.getText(R.string.credentials_email_validation_error))
        emailErrorEnabled.set(true)
    }

    fun clearEmailValidationError() {
        emailErrorText.set(null)
        emailErrorEnabled.set(false)
    }

    fun setPassword(password: CharSequence) {
        this.password.set(password)
        enableOrDisableContinueButton()
    }

    private fun enableOrDisableContinueButton() {
        val canContinue = email.get()?.isNotEmpty() == true
                && password.get()?.isNotEmpty() == true

        continueButtonEnabled.set(canContinue)
    }
}
