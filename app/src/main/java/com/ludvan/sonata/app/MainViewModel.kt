package com.ludvan.sonata.app

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ludvan.sonata.app.api.LoginApi
import com.ludvan.sonata.app.api.LoginBean
import com.ludvan.sonata.network.apiTransformer
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val api: LoginApi) : ViewModel() {

    fun login(context: Context): Observable<LoginBean> {
        val values = mapOf(
            "phone" to "13402801399",
            "authCode" to "897321"
        )
        return api.login(values).compose(apiTransformer(context, null, "login")).map { it.data }
    }

}