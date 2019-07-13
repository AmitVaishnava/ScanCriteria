package com.scancriteria.scandetails

import com.scancriteria.base.BasePresenter
import com.scancriteria.scanproperty.ScanProperty
import com.scancriteria.utils.Constants

class ScanDetailPresenter(scanDetails: ScanProperty.VariableObj) : BasePresenter<ScanDetailContract.ScanDetailView>(),
    ScanDetailContract.ScanDetailUserActionListener {


    var scanDetail = scanDetails


    override fun getScanDetail() {
        scanDetail?.let {
            if (scanDetail.type.equals(Constants.ScanVariableType.INDICATOR_TYPE)) {
                view()?.showScanDetailValue(scanDetail)
            } else
                scanDetail.values?.let { it1 -> view()?.showScanDetailList(it1) }
        }
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}