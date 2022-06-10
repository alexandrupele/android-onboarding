package rdx.works.wallet.onboarding.viewmodels

import androidx.databinding.ObservableField
import rdx.works.wallet.onboarding.model.OnboardingData

class PinViewModel {

    val pin = ObservableField<CharSequence>()
    val continueButtonEnabled = ObservableField<Boolean>()

    fun loadScreenState(data: OnboardingData) {
        data.pin?.let { pin.set(it) }
    }

    fun setPin(pin: CharSequence) {
        this.pin.set(pin)
        enableOrDisableContinueButton()
    }

    private fun enableOrDisableContinueButton() {
        val canContinue = pin.get()?.isNotEmpty() == true
        continueButtonEnabled.set(canContinue)
    }
}
