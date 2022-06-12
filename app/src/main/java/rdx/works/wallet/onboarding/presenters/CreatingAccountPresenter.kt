package rdx.works.wallet.onboarding.presenters

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.mvvm.DisposablePresenter
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.disposeWith
import rdx.works.wallet.core.rx.toObservable
import rdx.works.wallet.onboarding.actions.GoToDashboardAction
import rdx.works.wallet.onboarding.utils.CreateAccountPerformer
import java.util.concurrent.TimeUnit

private const val FAKE_LOADING_TIME_IN_SECONDS = 2L

class CreatingAccountPresenter(
    private val createAccountPerformer: CreateAccountPerformer
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("CreatingAccountPresenter") }

    val actions: Observable<PresenterAction> = emitter.hide()

    override fun createSubscriptions() {
        Observable
            .timer(FAKE_LOADING_TIME_IN_SECONDS, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .flatMap {
                createAccountPerformer
                    .createAccount()
                    .toObservable(GoToDashboardAction())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(emitter::onNext) {
                logger.error("Failed to create account", it)
            }
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
