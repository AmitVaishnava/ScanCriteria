package com.scancriteria.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.scancriteria.R

abstract class BaseFragment<T : BaseContract.BaseUserActionsListener> :
    Fragment(), BaseContract.BaseView {

    protected var mUserActionListener: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUserActionListener()
        if (mUserActionListener != null)
            mUserActionListener?.bindView(this)
    }

    override fun onStart() {
        super.onStart()
        if (mUserActionListener != null)
            mUserActionListener?.bindView(this)
    }

    //initialize presenter instance.
    protected abstract fun initUserActionListener()

    override fun onStop() {
        super.onStop()
        if (mUserActionListener != null)
            mUserActionListener?.unbindView()
    }

    override fun showNetworkError() {
        Toast.makeText(activity, getString(R.string.no_internet_connection_msg), Toast.LENGTH_SHORT)
    }

    override fun showErrorMsg(errorMsg: String) {
        Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT)
    }

    override fun showDefaultError() {
        Toast.makeText(activity, getString(R.string.default_error_msg), Toast.LENGTH_SHORT)
    }
}