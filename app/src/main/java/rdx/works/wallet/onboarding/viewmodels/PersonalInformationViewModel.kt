package rdx.works.wallet.onboarding.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

class PersonalInformationViewModel {

    val firstName = ObservableField<CharSequence>()
    val lastName = ObservableField<CharSequence>()
    val phoneNumber = ObservableField<CharSequence>()

    val continueButtonEnabled = ObservableBoolean()

    fun setFirstName(firstName: CharSequence) {
        this.firstName.set(firstName)
        enableOrDisableContinueButton()
    }

    fun setLastName(lastName: CharSequence) {
        this.lastName.set(lastName)
        enableOrDisableContinueButton()
    }

    fun setPhoneNumber(phoneNumber: CharSequence) {
        this.phoneNumber.set(phoneNumber)
        enableOrDisableContinueButton()
    }

    private fun enableOrDisableContinueButton() {
        val canContinue = firstName.get()?.isNotEmpty() == true
                && lastName.get()?.isNotEmpty() == true
                && phoneNumber.get()?.isNotEmpty() == true

        continueButtonEnabled.set(canContinue)
    }
}
