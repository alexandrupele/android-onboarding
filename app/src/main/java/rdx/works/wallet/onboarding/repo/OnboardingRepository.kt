package rdx.works.wallet.onboarding.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import rdx.works.wallet.onboarding.model.OnboardingData

class OnboardingRepository {

    private var data = OnboardingData()

    fun clear(): Completable = Completable.fromAction {
        data = OnboardingData()
    }

    fun getOnboardingData(): Single<OnboardingData> = Single.just(data)

    fun markTermsAsRead(): Completable = Completable.fromAction {
        data = data.copy(readTermsOfService = true)
    }

    fun storeCredentials(email: String, password: String): Completable = Completable.fromAction {
        data = data.copy(
            email = email,
            password = password
        )
    }

    fun storePersonalInformation(
        firstName: String,
        lastName: String,
        phoneNumber: String
    ): Completable = Completable.fromAction {
        data = data.copy(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber
        )
    }

    fun storePin(pin: String): Completable = Completable.fromAction {
        data = data.copy(
            pin = pin
        )
    }
}
