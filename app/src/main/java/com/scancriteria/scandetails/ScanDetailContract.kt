package com.scancriteria.scandetails

import com.scancriteria.base.BaseContract
import com.scancriteria.scanproperty.ScanProperty

class ScanDetailContract {

    interface ScanDetailView : BaseContract.BaseView {
        fun showProgressbar()
        fun hideProgressbar()
        fun showScanDetailList(scanList: List<String>)
        fun showScanDetailValue(scanValues: ScanProperty.VariableObj)
    }

    interface ScanDetailUserActionListener : BaseContract.BaseUserActionsListener {
        fun getScanDetail()
        fun onStop()
    }
}
