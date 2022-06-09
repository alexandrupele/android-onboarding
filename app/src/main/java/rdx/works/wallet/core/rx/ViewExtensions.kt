package rdx.works.wallet.core.rx

import android.widget.TextView
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import io.reactivex.rxjava3.core.Observable
import rdx.works.wallet.core.mvvm.uievents.TextViewAfterChangeEvent

fun TextView.textChangeEvents(): Observable<TextViewAfterChangeEvent> =
    afterTextChangeEvents()
        .map {
            TextViewAfterChangeEvent(
                viewId = it.view.id,
                text = it.editable ?: it.view.text
            )
        }
