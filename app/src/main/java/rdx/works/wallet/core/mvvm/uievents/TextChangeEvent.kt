package rdx.works.wallet.core.mvvm.uievents

import androidx.annotation.IdRes
import rdx.works.wallet.core.mvvm.UiEvent

data class TextChangeEvent(
    @IdRes val viewId: Int,
    val text: CharSequence
) : UiEvent
