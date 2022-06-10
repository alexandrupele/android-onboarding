package rdx.works.wallet.launcher.presenters

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.mvvm.DisposablePresenter
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.disposeWith
import rdx.works.wallet.core.session.SessionRepository
import rdx.works.wallet.launcher.actions.GoToDashboardAction
import rdx.works.wallet.launcher.actions.GoToOnboardingAction

class LauncherPresenter(
    private val sessionRepository: SessionRepository
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("LauncherPresenter") }

    val actions: Observable<PresenterAction> = emitter.hide()

    override fun createSubscriptions() {
        sessionRepository
            .getSession()
            .subscribeOn(Schedulers.io())
            .map {
                if (it.isSignedIn) {
                    GoToDashboardAction()
                } else {
                    GoToOnboardingAction()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(emitter::onNext) {
                logger.error("Could not determine session state", it)
            }
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
