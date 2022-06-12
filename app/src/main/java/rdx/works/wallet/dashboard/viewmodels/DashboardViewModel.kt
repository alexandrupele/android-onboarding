package rdx.works.wallet.dashboard.viewmodels

import android.content.res.Resources
import androidx.databinding.ObservableField
import rdx.works.wallet.R
import rdx.works.wallet.core.user.User

class DashboardViewModel(private val resources: Resources) {

    val greeting = ObservableField<CharSequence>()
    val contactDetails = ObservableField<CharSequence>()

    fun loadScreenState(user: User) {
        greeting.set(
            resources.getString(R.string.dashboard_greeting, user.firstName, user.lastName)
        )

        contactDetails.set(
            resources.getString(R.string.dashboard_contact_details, user.email, user.phoneNumber)
        )
    }
}
