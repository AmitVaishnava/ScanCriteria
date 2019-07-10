package com.scancriteria

import android.text.TextUtils
import com.scancriteria.base.BasePresenter
import com.scancriteria.network.NetworkUtils
import com.scancriteria.network.WebApi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ScanPropertiesPresenter : BasePresenter<ScanPropertiesContract.ScanPropertiesView>(),
    ScanPropertiesContract.ScanPropertiesUserActionListener {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    override fun loadScanPropertyList() {
        if (!NetworkUtils.isNetworkAvailable) {
            view()?.showErrorMsg("Network error")
            return
        }

        view()?.showProgressbar()
        uiScope.launch {
            var getScanPropertiesDeferred = WebApi.retrofitService.getScanData()
            try {
                val listResult = getScanPropertiesDeferred.await()
                view()?.showScanProperties(listResult)
                view()?.hideProgressbar()
            } catch (e: Exception) {
                view()?.hideProgressbar()
                if (!TextUtils.isEmpty(e.message))
                    view()?.showErrorMsg(e.message.toString())
                else
                    view()?.showDefaultError()
            }
        }

    }

    override fun onStop() {
        viewModelJob.cancel()
    }

}