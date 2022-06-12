package rdx.works.wallet.onboarding.utils

class EmailValidator {

    fun validateEmail(email: String): Boolean =
        email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
