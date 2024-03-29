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
import rdx.works.wallet.onboarding.actions.GoToCreatingAccountAction
import rdx.works.wallet.onboarding.repo.OnboardingRepository
import rdx.works.wallet.onboarding.viewmodels.ConfirmPinViewModel

class ConfirmPinPresenter(
    private val uiEvents: Observable<UiEvent>,
    private val onboardingRepository: OnboardingRepository,
    private val viewModel: ConfirmPinViewModel
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("ConfirmPinPresenter") }

    val actions: Observable<PresenterAction> = emitter.hide()

    override fun createSubscriptions() {
        uiEvents
            .ofType(TextChangeEvent::class.java)
            .filter {
                it.viewId == R.id.pin
            }
            .doOnNext {
                viewModel.setPin(it.text)
                viewModel.clearPinConfirmationError()
            }
            .subscribe()
            .disposeWith(disposables)

        val onboardingDataSource = onboardingRepository
            .getOnboardingData()
            .cache()

        uiEvents
            .ofType(ViewClickEvent::class.java)
            .filter {
                it.viewId == R.id.continueButton
            }
            .observeOn(Schedulers.io())
            .flatMapSingle {
                onboardingDataSource
            }
            .filter { onboardingData ->
                val isSamePin = onboardingData.pin == viewModel.pin.get().toString()
                isSamePin.also {
                    if (!it) viewModel.setPinConfirmationError()
                }
            }
            .map { GoToCreatingAccountAction() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(emitter::onNext) {
                logger.error("Failed to continue from pin confirmation", it)
            }
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
