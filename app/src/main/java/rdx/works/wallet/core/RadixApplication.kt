package rdx.works.wallet.core

import android.app.Application
import rdx.works.wallet.core.di.setupKoin

class RadixApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }
}
