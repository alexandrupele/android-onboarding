package rdx.works.wallet.core.di

import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import rdx.works.wallet.core.session.SessionRepository
import rdx.works.wallet.core.user.UserRepository

fun provideCoreModule() = module {

    factory { Gson() }

    factory {
        SessionRepository(
            sharedPreferences = androidContext().getSharedPreferences(
                SessionRepository.PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        )
    }

    factory {
        UserRepository(
            gson = get(),
            sharedPreferences = androidContext().getSharedPreferences(
                UserRepository.PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        )
    }
}
