package com.scancriteria.scandetails

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.scancriteria.R
import com.scancriteria.base.BaseFragment
import com.scancriteria.scanproperty.ScanProperty
import com.scancriteria.utils.UiUtils

class ScanPropertyDetailFragment : BaseFragment<ScanDetailContract.ScanDetailUserActionListener>(),
    ScanDetailContract.ScanDetailView {

    /**
     * variable declaration
     */
    lateinit var scanDetailVariable: ScanProperty.VariableObj
    lateinit var recyclerView: RecyclerView
    lateinit var scanDetailAdapter: ScanDetailAdapter
    lateinit var progressBar: View
    lateinit var constraintLayoutType: ConstraintLayout
    lateinit var studyTypeTextView: TextView
    lateinit var defaultValueEditText: EditText

    companion object {
        fun newInstance(values: ScanProperty.VariableObj): ScanPropertyDetailFragment {
            val args = Bundle()
            args.putParcelable("product_details", values)
            val fragment = ScanPropertyDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan_details, container, false)
    }

    override fun initUserActionListener() {
        if (arguments != null) {
            scanDetailVariable = arguments?.getParcelable("product_details")!!
        }
        mUserActionListener = ScanDetailPresenter(scanDetailVariable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defaultValueEditText = view.findViewById(R.id.default_value_edt_txt)
        studyTypeTextView = view.findViewById(R.id.study_type_txt_view)
        constraintLayoutType = view.findViewById(R.id.constrants_type)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view)
        scanDetailAdapter = ScanDetailAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = scanDetailAdapter
    }

    override fun onResume() {
        super.onResume()
        mUserActionListener?.getScanDetail()
    }

    override fun showProgressbar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        progressBar.visibility = View.GONE
    }

    override fun showScanDetailList(scanList: List<Double>) {
        scanDetailAdapter.scanDetails = scanList
    }

    override fun showScanDetailValue(scanValues: ScanProperty.VariableObj) {
        recyclerView.visibility = View.GONE
        constraintLayoutType.visibility = View.VISIBLE
        studyTypeTextView.text = scanValues.study_type?.toUpperCase()
        defaultValueEditText.setText(UiUtils.getFormattedValue(scanValues.default_value))
    }
}
