package rdx.works.wallet.core.rx

import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import io.reactivex.rxjava3.core.Observable
import rdx.works.wallet.core.mvvm.uievents.TextViewAfterChangeEvent
import rdx.works.wallet.core.mvvm.uievents.ViewClickEvent

fun TextView.textChangeEvents(): Observable<TextViewAfterChangeEvent> =
    afterTextChangeEvents()
        .map {
            TextViewAfterChangeEvent(
                viewId = it.view.id,
                text = it.editable ?: it.view.text
            )
        }

fun View.click(): Observable<ViewClickEvent> = Observable.create(ViewClickOnSubscribe(this))
