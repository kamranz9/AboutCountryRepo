package com.assignment.country.view.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.assignment.country.R
import com.assignment.country.databinding.FragmentAboutcanadaBinding
import com.assignment.country.model.data.RowEntity
import com.assignment.country.view.adapter.AboutCanadaAdapter
import com.assignment.country.viewmodel.AboutCanadaViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class AboutCanadaFragment : Fragment() {
    /* private val mBinding: FragmentAboutcanadaBinding by lazy {
         DataBindingUtil.setContentView<FragmentAboutcanadaBinding>(activi, R.layout.activity_about_canada)
     }
 */

    //di
    private val mViewModel: AboutCanadaViewModel by viewModel()
    private lateinit var mBinding: FragmentAboutcanadaBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAndObserveData()
        callSwipe()
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_aboutcanada, container, false)
        mBinding.vm = this.mViewModel
        return mBinding.root
    }

    private fun setRecyclerViewAndObserveData() {
        val adapter = AboutCanadaAdapter()
        handlingLayoutManager(mBinding.recyclerView)
        var rowl: List<RowEntity> = mutableListOf()
        rowl.let(adapter::submitList)
        mBinding.recyclerView.adapter = adapter
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            rowl = emptyList()
            rowl = it
            rowl.let(adapter::submitList)
            adapter.notifyDataSetChanged()
        })
    }


    private fun handlingLayoutManager(listUser: RecyclerView) {
        when {
            activity != null -> {
                val resources = requireActivity().resources
                if (resources != null) {
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        listUser.layoutManager =
                            StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
                    } else {
                        listUser.layoutManager =
                            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    }
                }
            }
        }
    }


    /**
     * Contains Refresh listener for Pull to Refresh Functionality
     */
    private fun callSwipe() {
        mBinding.swipeContainer.setOnRefreshListener {
            mViewModel.isLoading.set(true)
            mViewModel.fetchData()
        }
    }


}