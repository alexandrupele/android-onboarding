package rdx.works.wallet.core.mvvm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface DisposablePresenter {

    fun createSubscriptions()

    fun dispose()
}

fun DisposablePresenter.register(lifecycle: Lifecycle) {

    if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
        throw IllegalStateException("MUST call register(lifecycle) from onCreate()")
    }

    lifecycle.addObserver(object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            createSubscriptions()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            lifecycle.removeObserver(this)
            dispose()
        }
    })
}

fun Disposable.disposeWith(disposables: CompositeDisposable): Disposable {
    disposables.add(this)
    return this
}
