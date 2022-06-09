package rdx.works.wallet.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import rdx.works.wallet.core.mvvm.UiEvent

abstract class RadixActivity : AppCompatActivity() {

    val uiEvents: Observable<UiEvent> get() = uiEvents.hide()

    private val oneShotUiEvents = PublishSubject.create<UiEvent>()
    private val viewUiEventDisposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getContentView())
    }

    override fun onStart() {
        super.onStart()

        registerViewUiEventGenerators(collectViewUiEventsGenerators())
    }

    override fun onStop() {
        super.onStop()

        viewUiEventDisposables.clear()
    }

    @LayoutRes
    protected open fun getContentView() = 0

    open fun collectViewUiEventsGenerators() = emptyArray<Observable<out UiEvent>>()

    fun manuallyEmitUiEvent(event: UiEvent) {
        oneShotUiEvents.onNext(event)
    }

    private fun registerViewUiEventGenerators(generators: Array<Observable<out UiEvent>>) {
        for (generator in generators) {
            val disposable = generator.subscribe { event -> manuallyEmitUiEvent(event) }
            viewUiEventDisposables.addAll(disposable)
        }
    }
}
