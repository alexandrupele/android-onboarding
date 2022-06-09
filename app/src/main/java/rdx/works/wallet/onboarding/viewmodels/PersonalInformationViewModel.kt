package rdx.works.wallet.onboarding.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

class PersonalInformationViewModel {

    val firstName = ObservableField<CharSequence>()
    val lastName = ObservableField<CharSequence>()
    val phoneNumber = ObservableField<CharSequence>()

    val continueButtonEnabled = ObservableBoolean()

    fun setFirstName(firstName: String) {
        this.firstName.set(firstName)
        enableContinueButtonIfPossible()
    }

    private fun enableContinueButtonIfPossible() {
        continueButtonEnabled.set(
            firstName.get()?.isNotEmpty() == true
                    && lastName.get()?.isNotEmpty() == true
                    && phoneNumber.get()?.isNotEmpty() == true
        )
    }
}
