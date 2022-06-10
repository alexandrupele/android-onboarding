package rdx.works.wallet.onboarding.presenters

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.mvvm.DisposablePresenter
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.disposeWith
import rdx.works.wallet.onboarding.actions.GoToDashboardAction
import rdx.works.wallet.onboarding.logic.CreateAccountPerformer
import java.util.concurrent.TimeUnit

class CreatingAccountPresenter(
    private val createAccountPerformer: CreateAccountPerformer
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("CreatingAccountPresenter") }

    val actions: Observable<PresenterAction> = emitter.hide()

    override fun createSubscriptions() {
        Observable
            .timer(2, TimeUnit.SECONDS)
            .flatMapCompletable {
                createAccountPerformer
                    .createAccount()
                    .doOnComplete {
                        emitter.onNext(GoToDashboardAction())
                    }
            }
            .subscribeBy(onError = {
                logger.error("Failed to create account", it)
            })
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
