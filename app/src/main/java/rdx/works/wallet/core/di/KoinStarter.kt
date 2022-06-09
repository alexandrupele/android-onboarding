package rdx.works.wallet.core.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rdx.works.wallet.core.RadixApplication
import rdx.works.wallet.launcher.di.provideLauncherModule
import rdx.works.wallet.onboarding.di.provideOnboardingModule

fun RadixApplication.setupKoin() {

    startKoin {

        androidLogger(Level.ERROR)
        androidContext(this@setupKoin)

        modules(
            listOf(
                provideCoreModule(),
                provideLauncherModule(),
                provideOnboardingModule()
            )
        )
    }
}
