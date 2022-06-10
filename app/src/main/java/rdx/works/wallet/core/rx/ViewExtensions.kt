package rdx.works.wallet.core.rx

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import com.jakewharton.rxbinding4.widget.checkedChanges
import io.reactivex.rxjava3.core.Observable
import rdx.works.wallet.core.mvvm.uievents.CheckedChangeEvent
import rdx.works.wallet.core.mvvm.uievents.TextChangeEvent
import rdx.works.wallet.core.mvvm.uievents.ViewClickEvent

fun View.click(): Observable<ViewClickEvent> = Observable.create(ViewClickOnSubscribe(this))

fun AppCompatCheckBox.changeEvents(): Observable<CheckedChangeEvent> =
    checkedChanges()
        .skipInitialValue()
        .map { isChecked ->
            CheckedChangeEvent(
                viewId = id,
                isChecked = isChecked
            )
        }

fun TextView.changeEvents(): Observable<TextChangeEvent> =
    afterTextChangeEvents()
        .skipInitialValue()
        .map {
            TextChangeEvent(
                viewId = it.view.id,
                text = it.editable ?: it.view.text
            )
        }

