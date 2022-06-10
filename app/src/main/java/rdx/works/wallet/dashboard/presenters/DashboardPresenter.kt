package rdx.works.wallet.dashboard.presenters

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.mvvm.DisposablePresenter
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.UiEvent
import rdx.works.wallet.core.mvvm.disposeWith
import rdx.works.wallet.core.rx.toObservable
import rdx.works.wallet.core.user.UserRepository
import rdx.works.wallet.dashboard.events.SignOutEvent
import rdx.works.wallet.dashboard.logic.SignOutPerformer
import rdx.works.wallet.dashboard.viewmodels.DashboardViewModel
import rdx.works.wallet.launcher.actions.GoToOnboardingAction

class DashboardPresenter(
    private val uiEvents: Observable<UiEvent>,
    private val signOutPerformer: SignOutPerformer,
    private val userRepository: UserRepository,
    private val viewModel: DashboardViewModel
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("DashboardPresenter") }

    val actions: Observable<PresenterAction> = emitter.hide()

    override fun createSubscriptions() {
        userRepository
            .getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                viewModel.loadScreenState(user)
            }, {
                logger.error("Failed to load screen state", it)
            })

        uiEvents
            .ofType(SignOutEvent::class.java)
            .observeOn(Schedulers.io())
            .flatMap {
                signOutPerformer
                    .signOut()
                    .toObservable(GoToOnboardingAction())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(emitter::onNext) {
                logger.error("Failed to perform sign out", it)
            }
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
