package rdx.works.wallet.onboarding.presenters

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import rdx.works.wallet.R
import rdx.works.wallet.core.Logger
import rdx.works.wallet.core.mvvm.DisposablePresenter
import rdx.works.wallet.core.mvvm.PresenterAction
import rdx.works.wallet.core.mvvm.UiEvent
import rdx.works.wallet.core.mvvm.disposeWith
import rdx.works.wallet.core.mvvm.uievents.TextViewAfterChangeEvent
import rdx.works.wallet.onboarding.viewmodels.PersonalInformationViewModel

class PersonalInformationPresenter(
    private val uiEvents: Observable<UiEvent>,
    private val viewModel: PersonalInformationViewModel
) : DisposablePresenter {

    private val disposables = CompositeDisposable()
    private val emitter = PublishSubject.create<PresenterAction>()
    private val logger by lazy { Logger("PersonalInformationPresenter") }

    val actions: Observable<PresenterAction> = emitter.hide()

    override fun createSubscriptions() {
        uiEvents
            .ofType(TextViewAfterChangeEvent::class.java)
            .filter { it.viewId == R.id.firstName }
            .doOnNext {
                viewModel.setFirstName(it.text)
            }
            .subscribe()
            .disposeWith(disposables)
    }

    override fun dispose() {
        disposables.dispose()
    }
}
