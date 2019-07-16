package com.scancriteria.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

object FragmentHelper {

    fun replace(activity: FragmentActivity, container: Int, fragment: Fragment) {
        val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(container, fragment).commit()
    }

    fun addAddToBackStack(activity: FragmentActivity, container: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
        fragmentTransaction.add(container, fragment)
            .addToBackStack(tag)
            .commit()
    }
}
