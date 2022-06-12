package rdx.works.wallet.onboarding.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rdx.works.wallet.onboarding.activities.*
import rdx.works.wallet.onboarding.utils.CreateAccountPerformer
import rdx.works.wallet.onboarding.presenters.*
import rdx.works.wallet.onboarding.repo.OnboardingRepository
import rdx.works.wallet.onboarding.utils.EmailValidator
import rdx.works.wallet.onboarding.viewmodels.*

fun provideOnboardingModule() = module {

    single { OnboardingRepository() }

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
                onboardingRepository = get(),
                viewModel = get()
            )
        }
    }

    scope(named<TermsOfServiceActivity>()) {

        scoped {
            TermsOfServiceViewModel()
        }

        scoped { (activity: TermsOfServiceActivity) ->
            TermsOfServicePresenter(
                uiEvents = activity.uiEvents,
                onboardingRepository = get(),
                viewModel = get()
            )
        }
    }

    scope(named<PinActivity>()) {

        scoped {
            PinViewModel()
        }

        scoped { (activity: PinActivity) ->
            PinPresenter(
                uiEvents = activity.uiEvents,
                onboardingRepository = get(),
                viewModel = get()
            )
        }
    }

    scope(named<ConfirmPinActivity>()) {

        scoped {
            ConfirmPinViewModel(androidContext().resources)
        }

        scoped { (activity: ConfirmPinActivity) ->
            ConfirmPinPresenter(
                uiEvents = activity.uiEvents,
                onboardingRepository = get(),
                viewModel = get()
            )
        }
    }

    scope(named<CredentialsActivity>()) {

        scoped {
            CredentialsViewModel(resources = androidContext().resources)
        }

        scoped { (activity: CredentialsActivity) ->
            CredentialsPresenter(
                uiEvents = activity.uiEvents,
                emailValidator = EmailValidator(),
                onboardingRepository = get(),
                viewModel = get()
            )
        }
    }

    factory {
        CreatingAccountPresenter(
            createAccountPerformer = CreateAccountPerformer(
                onboardingRepository = get(),
                sessionRepository = get(),
                userRepository = get()
            )
        )
    }
}
