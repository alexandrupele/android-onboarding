package rdx.works.wallet.core.user

import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserRepository(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {

    fun clear(): Completable = Completable.fromAction {
        sharedPreferences
            .edit()
            .clear()
            .apply()
    }

    fun getUser(): Single<User> = Single.fromCallable {
        val serializedUser = sharedPreferences.getString(KEY_USER, null)
        gson.fromJson(serializedUser, User::class.java)
    }

    fun storeUser(user: User): Completable = Completable.fromAction {
        val serializedUser = gson.toJson(user)
        sharedPreferences
            .edit()
            .putString(KEY_USER, serializedUser)
            .apply()
    }

    companion object {

        const val PREFERENCES_NAME = "userPrefs"
        private const val KEY_USER = "user"
    }
}
