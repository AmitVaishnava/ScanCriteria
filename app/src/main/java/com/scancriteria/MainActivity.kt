package com.scancriteria

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.scancriteria.scanproperty.ScanPropertiesFragment
import com.scancriteria.scanproperty.ScanProperty

class MainActivity : AppCompatActivity(), ScanPropertiesFragment.ScanPropertiesFragmentListener {

    override fun OnItemClick(scanProperty: ScanProperty) {
        supportFragmentManager.beginTransaction()
            .add(R.id.parent_layout, ScanPropertiesFragment.newInstance(scanProperty))
            .addToBackStack(ScanPropertiesFragment.javaClass.name)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.parent_layout, ScanPropertiesFragment.newInstance())
            .commit()
    }
}
