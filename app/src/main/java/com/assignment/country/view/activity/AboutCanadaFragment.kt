package com.assignment.country.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.assignment.country.R
import com.assignment.country.databinding.FragmentAboutcanadaBinding
import com.assignment.country.helper.InternetCheckHelper
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
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        var listRowData: List<RowEntity> = mutableListOf()
        listRowData.let(adapter::submitList)
        mBinding.recyclerView.adapter = adapter
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            listRowData = emptyList()
            listRowData = it
            listRowData.let(adapter::submitList)
            adapter.notifyDataSetChanged()
        })

        mViewModel.titleLive.observe(viewLifecycleOwner, Observer {
            mViewModel.title.set(it)
        })

        mViewModel.response.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                1 -> {
                    mViewModel.userRecycler?.set(View.VISIBLE)
                    mViewModel.userLabel?.set(View.GONE)
                    mViewModel.messageLabel?.set("")
                }
                2 -> {
                    mViewModel.userLabel?.set(View.VISIBLE)
                    mViewModel.userRecycler?.set(View.GONE)
                    if (InternetCheckHelper.isInternetAvailable(requireContext())) {
                        mViewModel.getRowCount?.observe(viewLifecycleOwner, Observer {
                            if (!(it != null && it != 0)) mViewModel.messageLabel?.set(
                                context?.getString(
                                    R.string.error_no_data_available
                                )
                            )
                        })
                    } else {
                        mViewModel.messageLabel?.set(context?.getString(R.string.error_message_loading_internet))
                    }
                }
            }
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