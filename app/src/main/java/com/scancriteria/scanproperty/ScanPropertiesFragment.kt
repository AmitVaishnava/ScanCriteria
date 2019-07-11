package com.scancriteria.scanproperty

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scancriteria.R
import com.scancriteria.base.BaseFragment


class ScanPropertiesFragment : BaseFragment<ScanPropertiesContract.ScanPropertiesUserActionListener>(),
    ScanPropertiesContract.ScanPropertiesView,
    ScanPropertiesAdapter.ScanPropertiesAdapterListener {

    lateinit var recyclerView: RecyclerView
    lateinit var scanPropertiesAdapter: ScanPropertiesAdapter
    lateinit var progressBar: View
    lateinit var scanPropertiesFragmentListener: ScanPropertiesFragmentListener
    var mScanProperties: List<ScanProperty>? = null
    var scanProperty: ScanProperty? = null

    companion object {
        fun newInstance(): ScanPropertiesFragment {
            return ScanPropertiesFragment()
        }

        fun newInstance(scanProperty: ScanProperty): ScanPropertiesFragment {
            val args = Bundle()
            val fragment = ScanPropertiesFragment()
            args.putParcelable("scan_property", scanProperty)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ScanPropertiesFragmentListener)
            scanPropertiesFragmentListener = context
    }

    override fun initUserActionListener() {
        if (arguments != null) {
            scanProperty = arguments!!.getParcelable("scan_property")
        }
        mUserActionListener = ScanPropertiesPresenter(scanProperty)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan_properties, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view)
        scanPropertiesAdapter = ScanPropertiesAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
//        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = scanPropertiesAdapter
    }

    override fun onResume() {
        super.onResume()
        mUserActionListener?.loadScanPropertyList()
    }

    override fun onStop() {
        super.onStop()
        mUserActionListener?.onStop()
    }

    override fun showProgressbar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        progressBar.visibility = View.GONE
    }

    override fun showScanProperties(scanProperties: List<ScanProperty>) {
        Log.d(ScanPropertiesFragment.javaClass.name, "data: " + scanProperties.size)
        scanPropertiesAdapter.scanProperties = scanProperties
        mScanProperties = scanProperties
    }

    override fun showScanProperty(scanProperty: ScanProperty) {
        mScanProperties = listOf(scanProperty)
        scanPropertiesAdapter.scanProperties = mScanProperties as List<ScanProperty>
        scanPropertiesAdapter.enableChildView = true
    }

    override fun onItemClick(position: Int) {
        mScanProperties?.get(position)?.let { scanPropertiesFragmentListener.OnItemClick(it) }
    }

    interface ScanPropertiesFragmentListener {
        fun OnItemClick(scanProperty: ScanProperty)
    }
}
