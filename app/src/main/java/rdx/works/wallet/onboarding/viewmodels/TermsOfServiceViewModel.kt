package rdx.works.wallet.onboarding.viewmodels

import androidx.databinding.ObservableBoolean

class TermsOfServiceViewModel {

    val checkBoxChecked = ObservableBoolean()
    val continueButtonEnabled = ObservableBoolean()

    fun setContinueButtonEnabled(isEnabled: Boolean) {
        continueButtonEnabled.set(isEnabled)
    }

    fun setCheckBoxAsChecked(isChecked: Boolean) {
        checkBoxChecked.set(isChecked)
    }
}
