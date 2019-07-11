package com.scancriteria.scanproperty

import com.scancriteria.base.BaseContract

class ScanPropertiesContract {
    interface ScanPropertiesView : BaseContract.BaseView {
        fun showProgressbar()
        fun hideProgressbar()
        fun showScanProperties(scanProperties: List<ScanProperty>)
        fun showScanProperty(scanProperty: ScanProperty)
    }

    interface ScanPropertiesUserActionListener : BaseContract.BaseUserActionsListener {
        fun loadScanPropertyList()
        fun onStop()
    }
}