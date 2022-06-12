package rdx.works.wallet.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.androidx.scope.ScopeActivity
import rdx.works.wallet.R
import rdx.works.wallet.core.mvvm.UiEvent

abstract class RadixActivity : ScopeActivity() {

    val uiEvents: Observable<UiEvent> get() = oneShotUiEvents.hide()

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
            val disposable = generator.subscribe { event ->
                manuallyEmitUiEvent(event)
            }
            viewUiEventDisposables.addAll(disposable)
        }
    }

    protected open fun animateOpeningTransition() {
        overridePendingTransition(R.anim.enter_direction_left, R.anim.exit_direction_left)
    }

    protected open fun animateClosingTransition() {
        overridePendingTransition(R.anim.enter_direction_right, R.anim.exit_direction_right)
    }
}
