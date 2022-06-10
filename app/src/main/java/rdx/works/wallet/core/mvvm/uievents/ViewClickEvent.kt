package rdx.works.wallet.core.mvvm.uievents

import androidx.annotation.IdRes
import rdx.works.wallet.core.mvvm.UiEvent

data class ViewClickEvent(@IdRes val viewId: Int) : UiEvent
