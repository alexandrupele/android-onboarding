package rdx.works.wallet.onboarding.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import rdx.works.wallet.onboarding.model.OnboardingData

class CredentialsViewModel {

    val email = ObservableField<CharSequence>()
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
