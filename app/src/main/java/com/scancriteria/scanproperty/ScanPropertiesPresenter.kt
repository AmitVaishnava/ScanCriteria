package com.scancriteria.scanproperty

import android.text.TextUtils
import android.util.Log
import com.scancriteria.base.BasePresenter
import com.scancriteria.network.NetworkUtils
import com.scancriteria.network.WebApi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ScanPropertiesPresenter(scanProperty: ScanProperty?) : BasePresenter<ScanPropertiesContract.ScanPropertiesView>(),
    ScanPropertiesContract.ScanPropertiesUserActionListener {

    private var mScanProperty = scanProperty
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    override fun loadScanPropertyList() {
        if (mScanProperty != null) {
            view()?.showScanProperty(mScanProperty!!)
            return
        }

        if (!NetworkUtils.isNetworkAvailable) {
            view()?.showErrorMsg("Network error")
            return
        }

        uiScope.launch {
            view()?.showProgressbar()
            var getScanPropertiesDeferred = WebApi.retrofitService.getScanData()
            try {
                val listResult = getScanPropertiesDeferred.await()
                Log.d("amit", "response:" + listResult)
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