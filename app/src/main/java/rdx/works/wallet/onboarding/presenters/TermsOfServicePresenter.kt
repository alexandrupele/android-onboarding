package rdx.works.wallet.onboarding.presenters

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import rdx.works.wallet.R
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.mvvm.DisposablePresenter
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.UiEvent
import rdx.works.wallet.core.mvvm.disposeWith
import rdx.works.wallet.core.mvvm.uievents.CheckedChangeEvent
import rdx.works.wallet.core.mvvm.uievents.ViewClickEvent
import rdx.works.wallet.onboarding.repo.OnboardingRepository
import rdx.works.wallet.onboarding.actions.GoToCredentialsAction
import rdx.works.wallet.onboarding.viewmodels.TermsOfServiceViewModel

class TermsOfServicePresenter(
    private val uiEvents: Observable<UiEvent>,
    private val onboardingRepository: OnboardingRepository,
    private val viewModel: TermsOfServiceViewModel
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("TermsOfServicePresenter") }

    val actions: Observable<PresenterAction> = emitter.hide()

    override fun createSubscriptions() {
        onboardingRepository
            .getOnboardingData()
            .subscribe({
                viewModel.setCheckBoxAsChecked(it.readTermsOfService ?: false)
            }, {
                logger.error("Failed to load the state of the screen", it)
            })
            .disposeWith(disposables)

        uiEvents
            .ofType(CheckedChangeEvent::class.java)
            .filter {
                it.viewId == R.id.checkBox
            }
            .doOnNext {
                viewModel.setContinueButtonEnabled(it.isChecked)
            }
            .subscribeBy(onError = {
                logger.error("Could not mark terms of service as read", it)
            })
            .disposeWith(disposables)

        uiEvents
            .ofType(ViewClickEvent::class.java)
            .filter {
                it.viewId == R.id.continueButton
            }
            .flatMapCompletable {
                onboardingRepository
                    .markTermsAsRead()
                    .doOnComplete {
                        emitter.onNext(GoToCredentialsAction())
                    }
            }
            .subscribeBy(onError = {
                logger.error("Could not navigate from terms of service", it)
            })
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
