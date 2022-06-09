package rdx.works.wallet.launcher.di

import org.koin.dsl.module
import rdx.works.wallet.launcher.LauncherPresenter

fun provideLauncherModule() = module {

    factory {
        LauncherPresenter(
            sessionRepository = get()
        )
    }
}
