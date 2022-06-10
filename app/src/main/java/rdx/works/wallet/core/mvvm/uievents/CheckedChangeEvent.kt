package rdx.works.wallet.core.mvvm.uievents

import androidx.annotation.IdRes
import rdx.works.wallet.core.mvvm.UiEvent

data class CheckedChangeEvent(
    @IdRes val viewId: Int,
    val isChecked: Boolean
) : UiEvent
