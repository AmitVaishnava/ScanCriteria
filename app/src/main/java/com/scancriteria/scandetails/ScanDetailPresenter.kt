package com.scancriteria.scandetails

import com.scancriteria.base.BasePresenter
import com.scancriteria.scanproperty.ScanProperty
import com.scancriteria.utils.Constants

class ScanDetailPresenter(scanDetails: ScanProperty.VariableObj) : BasePresenter<ScanDetailContract.ScanDetailView>(),
    ScanDetailContract.ScanDetailUserActionListener {


    private var scanDetail = scanDetails


    override fun getScanDetail() {
        view()?.showProgressbar()
        scanDetail?.let {
            if (scanDetail.type.equals(Constants.ScanVariableType.INDICATOR_TYPE)) {
                view()?.hideProgressbar()
                view()?.showScanDetailValue(scanDetail)
            } else {
                view()?.hideProgressbar()
                scanDetail.values?.let { it1 -> view()?.showScanDetailList(it1.sorted()) }
            }
        }
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}