package com.assignment.aboutcountryproject.view.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.aboutcountryproject.BuildConfig
import com.assignment.aboutcountryproject.R
import com.assignment.aboutcountryproject.databinding.FragmentAboutcanadaBinding
import com.assignment.aboutcountryproject.view.adapter.AboutCanadaAdapter
import com.assignment.aboutcountryproject.viewmodel.AboutCanadaViewModel
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Single
import org.koin.android.viewmodel.ext.android.viewModel


class AboutCanadaFragment : Fragment() {

   /* private val mBinding:FragmentAboutcanadaBinding  by lazy {
        DataBindingUtil.setContentView<FragmentAboutcanadaBinding>(requireactivity(), R.layout.fragment_aboutcanada)
    }*/

    //di
    private val mViewModel: AboutCanadaViewModel by viewModel()
    lateinit var mBinding: FragmentAboutcanadaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_aboutcanada, container, false
        )
        mBinding.vm = mViewModel
        val view: View = mBinding.root


        mViewModel.getCountryDetailsList()
            .bindLifeCycle(this)
            .subscribe({}, { dispatchFailure(it) })

        setRecyclerView(mBinding.recyclerView)




        return  view
    }



    private fun dispatchFailure(error: Throwable?, length: Int = Toast.LENGTH_SHORT) {
        error?.let {
            if (BuildConfig.DEBUG) {
                it.printStackTrace()
            }
            Toast.makeText(context, it.message, length).show()
        }
    }
    private fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))

    private fun setRecyclerView(recyclerView: RecyclerView) {
        var adapter = AboutCanadaAdapter()
        /*val categoryLinearLayoutManager = LinearLayoutManager(context)
        categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = categoryLinearLayoutManager
        recyclerView.adapter = adapter*/
        mBinding.adapter = adapter

        mViewModel.data.observe(viewLifecycleOwner, Observer {
            Log.e("MainActivity", it.toString())
            it.let(adapter::submitList)
        })
    }


}