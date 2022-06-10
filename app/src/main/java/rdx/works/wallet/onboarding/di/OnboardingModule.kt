package rdx.works.wallet.onboarding.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import rdx.works.wallet.onboarding.activities.PersonalInformationActivity
import rdx.works.wallet.onboarding.activities.WelcomeActivity
import rdx.works.wallet.onboarding.presenters.PersonalInformationPresenter
import rdx.works.wallet.onboarding.presenters.WelcomePresenter
import rdx.works.wallet.onboarding.viewmodels.PersonalInformationViewModel

fun provideOnboardingModule() = module {

    scope(named<WelcomeActivity>()) {

        scoped { (activity: WelcomeActivity) ->
            WelcomePresenter(
                uiEvents = activity.uiEvents
            )
        }
    }

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
