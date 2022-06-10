package rdx.works.wallet.onboarding.presenters

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import rdx.works.wallet.R
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.mvvm.DisposablePresenter
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.UiEvent
import rdx.works.wallet.core.mvvm.disposeWith
import rdx.works.wallet.core.mvvm.uievents.TextChangeEvent
import rdx.works.wallet.core.mvvm.uievents.ViewClickEvent
import rdx.works.wallet.onboarding.repo.OnboardingRepository
import rdx.works.wallet.onboarding.actions.GoToPersonalInformationAction
import rdx.works.wallet.onboarding.viewmodels.CredentialsViewModel

class CredentialsPresenter(
    private val uiEvents: Observable<UiEvent>,
    private val onboardingRepository: OnboardingRepository,
    private val viewModel: CredentialsViewModel
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("CredentialsPresenter") }

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
                it.viewId == R.id.email
            }
            .doOnNext {
                viewModel.setEmail(it.text)
            }
            .subscribe()
            .disposeWith(disposables)

        uiEvents
            .ofType(TextChangeEvent::class.java)
            .filter {
                it.viewId == R.id.password
            }
            .doOnNext {
                viewModel.setPassword(it.text)
            }
            .subscribe()
            .disposeWith(disposables)

        uiEvents
            .ofType(ViewClickEvent::class.java)
            .filter {
                it.viewId == R.id.continueButton
            }
            .flatMapCompletable {
                onboardingRepository.storeCredentials(
                    email = viewModel.email.get().toString(),
                    password = viewModel.password.get().toString()
                ).doOnComplete {
                    emitter.onNext(GoToPersonalInformationAction())
                }
            }
            .subscribe()
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
