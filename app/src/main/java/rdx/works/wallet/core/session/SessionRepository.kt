package rdx.works.wallet.core.session

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single

class SessionRepository(private val sharedPreferences: SharedPreferences) {

    fun getSession(): Single<Session> = Single.fromCallable {
        Session(sharedPreferences.getBoolean(IS_SIGNED_IN, false))
    }

    fun storeSignedInStatus(isSignedIn: Boolean): Completable = Completable.fromAction {
        sharedPreferences
            .edit()
            .putBoolean(IS_SIGNED_IN, isSignedIn)
            .apply()
    }

    companion object {

        const val PREFERENCES_NAME = "sessionPrefs"
        private const val IS_SIGNED_IN = "signedIn"
    }
}
