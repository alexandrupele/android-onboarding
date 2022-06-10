package rdx.works.wallet.core.user

data class User(
    val firstName: String,
    val lastName: String,
    val password: String,
    val pin: String,
    val email: String
)
