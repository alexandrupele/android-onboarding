package rdx.works.wallet.onboarding.model

data class OnboardingData(
    val readTermsOfService: Boolean? = null,
    val email: String? = null,
    val password: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNumber: String? = null,
    val pin: String? = null
)
