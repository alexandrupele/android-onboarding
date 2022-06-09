package rdx.works.wallet.onboarding.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import rdx.works.wallet.onboarding.activities.PersonalInformationActivity
import rdx.works.wallet.onboarding.presenters.PersonalInformationPresenter
import rdx.works.wallet.onboarding.viewmodels.PersonalInformationViewModel

fun provideOnboardingModule() = module {

    scope(named<PersonalInformationActivity>()) {

        scoped {
            PersonalInformationViewModel()
        }

        scoped { (activity: PersonalInformationActivity) ->
            PersonalInformationPresenter(
                uiEvents = activity.uiEvents,
                viewModel = get()
            )
        }
    }
}
