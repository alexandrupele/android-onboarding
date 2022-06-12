package rdx.works.wallet.onboarding.presenters

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import rdx.works.wallet.R
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.mvvm.DisposablePresenter
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.UiEvent
import rdx.works.wallet.core.mvvm.disposeWith
import rdx.works.wallet.core.mvvm.uievents.TextChangeEvent
import rdx.works.wallet.core.mvvm.uievents.ViewClickEvent
import rdx.works.wallet.core.rx.toObservable
import rdx.works.wallet.onboarding.actions.GoToConfirmPinAction
import rdx.works.wallet.onboarding.repo.OnboardingRepository
import rdx.works.wallet.onboarding.viewmodels.PinViewModel

class PinPresenter(
    private val uiEvents: Observable<UiEvent>,
    private val onboardingRepository: OnboardingRepository,
    private val viewModel: PinViewModel
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("PinPresenter") }

    val actions: Observable<PresenterAction> = emitter.hide()

    override fun createSubscriptions() {
        onboardingRepository
            .getOnboardingData()
            .subscribe({
                viewModel.loadScreenState(it)
            }, {
                logger.error("Failed to load screen state", it)
            })
            .disposeWith(disposables)

        uiEvents
            .ofType(TextChangeEvent::class.java)
            .filter {
                it.viewId == R.id.pin
            }
            .doOnNext {
                viewModel.setPin(it.text)
            }
            .subscribe()
            .disposeWith(disposables)

        uiEvents
            .ofType(ViewClickEvent::class.java)
            .filter {
                it.viewId == R.id.continueButton
            }
            .observeOn(Schedulers.io())
            .flatMap {
                onboardingRepository
                    .storePin(viewModel.pin.get().toString())
                    .toObservable(GoToConfirmPinAction())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(emitter::onNext) {
                logger.error("Failed to continue from pin", it)
            }
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
