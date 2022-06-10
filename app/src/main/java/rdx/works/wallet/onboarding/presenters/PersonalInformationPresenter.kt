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
import rdx.works.wallet.onboarding.actions.GoToPinAction
import rdx.works.wallet.onboarding.viewmodels.PersonalInformationViewModel

class PersonalInformationPresenter(
    private val uiEvents: Observable<UiEvent>,
    private val onboardingRepository: OnboardingRepository,
    private val viewModel: PersonalInformationViewModel
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("PersonalInformationPresenter") }

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
                it.viewId == R.id.firstName
            }
            .doOnNext {
                viewModel.setFirstName(it.text)
            }
            .subscribe()
            .disposeWith(disposables)

        uiEvents
            .ofType(TextChangeEvent::class.java)
            .filter {
                it.viewId == R.id.lastName
            }
            .doOnNext {
                viewModel.setLastName(it.text)
            }
            .subscribe()
            .disposeWith(disposables)

        uiEvents
            .ofType(TextChangeEvent::class.java)
            .filter {
                it.viewId == R.id.phoneNumber
            }
            .doOnNext {
                viewModel.setPhoneNumber(it.text)
            }
            .subscribe()
            .disposeWith(disposables)

        uiEvents
            .ofType(ViewClickEvent::class.java)
            .filter {
                it.viewId == R.id.continueButton
            }
            .flatMapCompletable {
                onboardingRepository
                    .storePersonalInformation(
                        firstName = viewModel.firstName.get().toString(),
                        lastName = viewModel.lastName.get().toString(),
                        phoneNumber = viewModel.phoneNumber.get().toString()
                    ).doOnComplete {
                        emitter.onNext(GoToPinAction())
                    }
            }
            .subscribe()
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
