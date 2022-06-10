package rdx.works.wallet.launcher.di

import org.koin.dsl.module
import rdx.works.wallet.launcher.presenters.LauncherPresenter

fun provideLauncherModule() = module {

    factory {
        LauncherPresenter(
            sessionRepository = get()
        )
    }
}
