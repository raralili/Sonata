package com.ludvan.sonata.network

class ApiProcessOption {


    var loading: Loading? = null
        private set

    var loadingSettings = LoadingSettings()
        private set

    var requireData = false
        private set

    fun setLoading(loading: Loading): ApiProcessOption {
        this.loading = loading
        return this
    }

    fun setLoading(loading: Loading, loadingSettings: LoadingSettings): ApiProcessOption {
        this.loadingSettings = loadingSettings
        return this
    }

    fun requireData(require: Boolean): ApiProcessOption {
        this.requireData = require
        return this
    }


    class LoadingSettings {
        var showLoadingBefore = true
        var hideLoadingOnSuccess = true
        var hideLoadingOnFailed = true


        fun showLoadingBefore(show: Boolean): LoadingSettings {
            showLoadingBefore = show
            return this
        }

        fun hideLoadingOnSuccess(hide: Boolean): LoadingSettings {
            hideLoadingOnSuccess = hide
            return this
        }

        fun hideLoadingOnFailed(hide: Boolean): LoadingSettings {
            hideLoadingOnFailed = hide
            return this
        }


    }

}