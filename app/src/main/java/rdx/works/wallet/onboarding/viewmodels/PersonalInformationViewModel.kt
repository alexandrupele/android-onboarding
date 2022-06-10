package rdx.works.wallet.onboarding.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import rdx.works.wallet.onboarding.model.OnboardingData

class PersonalInformationViewModel {

    val firstName = ObservableField<CharSequence>()
    val lastName = ObservableField<CharSequence>()
    val phoneNumber = ObservableField<CharSequence>()

    val continueButtonEnabled = ObservableBoolean()

    fun loadScreenState(data: OnboardingData) {
        data.firstName?.let { firstName.set(it) }
        data.lastName?.let { lastName.set(it) }
        data.phoneNumber?.let { phoneNumber.set(it) }
    }

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
