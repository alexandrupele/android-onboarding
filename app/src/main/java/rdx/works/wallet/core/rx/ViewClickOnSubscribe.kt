package rdx.works.wallet.core.rx

import android.view.View
import io.reactivex.rxjava3.android.MainThreadDisposable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import rdx.works.wallet.core.mvvm.uievents.ViewClickEvent

class ViewClickOnSubscribe(val view: View) : ObservableOnSubscribe<ViewClickEvent> {

    override fun subscribe(emitter: ObservableEmitter<ViewClickEvent>) {

        view.setOnClickListener { emitter.onNext(ViewClickEvent(it.id)) }

        emitter.setDisposable(object : MainThreadDisposable() {
            override fun onDispose() {
                view.setOnClickListener(null)
            }

        })
    }
}
