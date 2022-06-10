package rdx.works.wallet.onboarding.viewmodels

import android.content.res.Resources
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import rdx.works.wallet.R

class ConfirmPinViewModel(private val resources: Resources) {

    val pin = ObservableField<CharSequence>()
    val errorText = ObservableField<CharSequence>()
    val errorEnabled = ObservableBoolean()
    val continueButtonEnabled = ObservableField<Boolean>()

    fun setPin(pin: CharSequence) {
        this.pin.set(pin)
        enableOrDisableContinueButton()
    }

    fun setPinConfirmationError() {
        errorText.set(resources.getText(R.string.confirm_pin_error))
        errorEnabled.set(true)
    }

    fun clearPinConfirmationError() {
        errorEnabled.set(false)
    }

    private fun enableOrDisableContinueButton() {
        val canContinue = pin.get()?.isNotEmpty() == true
        continueButtonEnabled.set(canContinue)
    }
}
