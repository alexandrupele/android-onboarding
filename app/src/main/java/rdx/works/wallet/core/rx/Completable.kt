package rdx.works.wallet.core.rx

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

fun <T : Any> Completable.toObservable(item: T): Observable<T> =
    toObservable<T>().concatWith(Observable.just(item))
