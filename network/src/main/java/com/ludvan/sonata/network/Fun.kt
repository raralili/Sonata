package com.ludvan.sonata.network

import android.content.Context
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

fun <T> Observable<T>.subscribe(d: CompositeDisposable, onNext: (T) -> Unit) {
    d.add(this.subscribe(onNext, {}))
}

fun <T> apiTransformer(
    context: Context,
    processOption: ApiProcessOption? = null,
    method: String? = null,
    errorCodeInterceptor: ((ApiResult<T>) -> Boolean)? = null
): ApiTransformer<T> {
    return ApiTransformer<T>(context, processOption ?: ApiProcessOption(), method, errorCodeInterceptor)
}