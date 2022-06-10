package rdx.works.wallet.dashboard.presenters

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.mvvm.DisposablePresenter
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.UiEvent
import rdx.works.wallet.core.mvvm.disposeWith
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
            .subscribe({
                viewModel.loadScreenState(it)
            }, {

            })

        uiEvents
            .ofType(SignOutEvent::class.java)
            .flatMapCompletable {
                signOutPerformer
                    .signOut()
                    .doOnComplete {
                        emitter.onNext(GoToOnboardingAction())
                    }
            }
            .subscribe()
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
