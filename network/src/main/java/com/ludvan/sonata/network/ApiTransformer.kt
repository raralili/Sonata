package com.ludvan.sonata.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers


class ApiTransformer<T> @JvmOverloads constructor(
    val context: Context,
    val processOption: ApiProcessOption,
    val method: String? = null,
    val errorCodeInterceptor: ((ApiResult<T>) -> Boolean)? = null
) : ObservableTransformer<ApiResult<T>, ApiResult<T>> {

    companion object {

        const val TAG = "ApiTransformer"
        const val CODE_OK = "0000"
        const val CODE_TOKEN_OUT = "-1"
        const val CODE_FAILED = "9999"

        const val LOADING_PROCESS_BEFORE = 1
        const val LOADING_PROCESS_NEXT = 2
        const val LOADING_PROCESS_ERROR = 3
    }

    private fun checkLoading(process: Int): Boolean {
        return when (process) {
            LOADING_PROCESS_BEFORE -> processOption.loadingSettings.showLoadingBefore
            LOADING_PROCESS_NEXT -> processOption.loadingSettings.hideLoadingOnSuccess
            LOADING_PROCESS_ERROR -> processOption.loadingSettings.hideLoadingOnFailed
            else -> true
        }
    }

    override fun apply(upstream: Observable<ApiResult<T>>): ObservableSource<ApiResult<T>> {

        if (checkLoading(LOADING_PROCESS_BEFORE)) processOption.loading?.show()

        val o = upstream
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (checkLoading(LOADING_PROCESS_NEXT)) processOption.loading?.hide()

            }.flatMap { result ->
                if (result.code == CODE_OK) {
                    Observable.just(result)
                } else {
                    val intercepted = errorCodeInterceptor?.invoke(result) ?: false
                    if (!intercepted) {
                        showToast(result.msg)
                    }
                    Observable.error(ApiException(result))
                }
            }
            .doOnError {
                if (checkLoading(LOADING_PROCESS_ERROR)) processOption.loading?.hide()
                if (it is ApiException) {
                    Log.e(TAG, "${method ?: TAG}--API_ERR>>>code = ${it.result.code},msg = ${it.message}")
                } else {
                    Log.e(TAG, "${method ?: TAG}--${it.message}", it)
                    showToast("网络异常")
                }
            }
        return o
    }


    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }


}