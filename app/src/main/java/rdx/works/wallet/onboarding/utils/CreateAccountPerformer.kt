package rdx.works.wallet.onboarding.utils

import io.reactivex.rxjava3.core.Completable
import rdx.works.wallet.core.session.SessionRepository
import rdx.works.wallet.core.user.User
import rdx.works.wallet.core.user.UserRepository
import rdx.works.wallet.onboarding.repo.OnboardingRepository

class CreateAccountPerformer(
    private val onboardingRepository: OnboardingRepository,
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
) {

    fun createAccount(): Completable =
        onboardingRepository
            .getOnboardingData()
            .map {
                User(
                    firstName = it.firstName!!,
                    lastName = it.lastName!!,
                    password = it.password!!,
                    pin = it.pin!!,
                    email = it.email!!,
                    phoneNumber = it.phoneNumber!!
                )
            }
            .flatMapCompletable {
                userRepository
                    .storeUser(it)
                    .andThen(onboardingRepository.clear())
                    .andThen(sessionRepository.storeSignedInStatus(true))
            }
}
