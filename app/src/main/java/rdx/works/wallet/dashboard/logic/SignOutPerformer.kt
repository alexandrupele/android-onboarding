package rdx.works.wallet.dashboard.logic

import io.reactivex.rxjava3.core.Completable
import rdx.works.wallet.core.session.SessionRepository
import rdx.works.wallet.core.user.UserRepository

class SignOutPerformer(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository
) {

    fun signOut(): Completable =
        userRepository
            .clear()
            .andThen(sessionRepository.storeSignedInStatus(false))
}
