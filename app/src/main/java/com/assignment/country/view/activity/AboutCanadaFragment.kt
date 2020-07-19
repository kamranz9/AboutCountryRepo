package com.assignment.country.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.assignment.country.R
import com.assignment.country.databinding.FragmentAboutcanadaBinding
import com.assignment.country.model.data.RowEntity
import com.assignment.country.view.adapter.AboutCanadaAdapter
import com.assignment.country.viewmodel.AboutCanadaViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class AboutCanadaFragment : Fragment() {
    //di
    private val mViewModel: AboutCanadaViewModel by viewModel()
    private lateinit var mBinding: FragmentAboutcanadaBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAndObserveData()
        callSwipe()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_aboutcanada, container, false)
        mBinding.vm = this.mViewModel
        return mBinding.root
    }

    private fun setRecyclerViewAndObserveData() {
        val adapter = AboutCanadaAdapter()
        var listRowData: List<RowEntity> = mutableListOf()
        listRowData.let(adapter::submitList)
        mBinding.recyclerView.adapter = adapter
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            listRowData = emptyList()
            listRowData = it
            listRowData.let(adapter::submitList)
            adapter.notifyDataSetChanged()
        })
    }

    /**
     * Contains Refresh listener for Pull to Refresh Functionality
     */
    private fun callSwipe() {
        mBinding.swipeContainer.setOnRefreshListener {
            mViewModel.isLoading.set(true)
            mViewModel.callAPI()
        }
    }
}