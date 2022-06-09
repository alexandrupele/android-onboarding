package rdx.works.wallet.core.rx

import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import rdx.works.wallet.core.mvvm.uievents.TextViewAfterChangeEvent

fun TextView.afterTextChangeEvents(): Observable<TextViewAfterChangeEvent> = RxTextView
    .afterTextChangeEvents(this)
    .map {
        TextViewAfterChangeEvent(
            viewId = it.view().id,
            text = it.editable() ?: it.view().text
        )
    }
