package com.scancriteria

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.scancriteria.scandetails.ScanPropertyDetailFragment
import com.scancriteria.scanproperty.ScanPropertiesFragment
import com.scancriteria.scanproperty.ScanProperty
import com.scancriteria.utils.FragmentHelper

class MainActivity : AppCompatActivity(), ScanPropertiesFragment.ScanPropertiesFragmentListener {

    override fun onSubItemSelected(values: ScanProperty.VariableObj?) {
        values?.let {
            FragmentHelper.addAddToBackStack(
                this, R.id.parent_layout, ScanPropertyDetailFragment.newInstance(values),
                ScanPropertyDetailFragment.javaClass.name
            )
        }
    }

    override fun OnItemClick(scanProperty: ScanProperty) {
        FragmentHelper.addAddToBackStack(
            this,
            R.id.parent_layout,
            ScanPropertiesFragment.newInstance(scanProperty),
            ScanPropertiesFragment.javaClass.name
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FragmentHelper.replace(this, R.id.parent_layout, ScanPropertiesFragment.newInstance())
    }
}
