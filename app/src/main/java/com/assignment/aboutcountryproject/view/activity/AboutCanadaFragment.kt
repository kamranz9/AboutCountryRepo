package com.assignment.aboutcountryproject.view.activity

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
    private lateinit var mBinding: FragmentAboutcanadaBinding

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


        if (isInternetAvailable()) {
            callAPI()
            setRecyclerView()
        } else {
            callAPI()
            setRecyclerView()
        }

        return view
    }

    /**
     * Method to Check Internet Connection
     *
     * @return false if internet is not connected
     */
    @Suppress("DEPRECATION")
    fun isInternetAvailable(): Boolean {
        var result = false
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }



    private fun callAPI() {
        mViewModel.getCountryDetailsList()
            .bindLifeCycle(this)
            .subscribe({}, { dispatchFailure(it) })
    }


/*    object DataBindingAdapter {
        @BindingAdapter(value = ["setAdapter"])
        @JvmStatic
        fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
            this.run {
                this.adapter = adapter
                this.setHasFixedSize(true)
            }
        }
    }*/

    private fun dispatchFailure(error: Throwable?, length: Int = Toast.LENGTH_SHORT) {
        error?.let {
            if (BuildConfig.DEBUG) {
                it.printStackTrace()
            }
            Toast.makeText(context, it.message, length).show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(1,LinearLayoutManager.VERTICAL)
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        }
    }

    private fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner,Lifecycle.Event.ON_DESTROY)))

    private fun setRecyclerView() {
        val adapter = AboutCanadaAdapter()
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            Log.e("MainActivity", it.toString())
            it.let(adapter::submitList)
        })

        handlingLayoutManager(mBinding.recyclerView)
        mBinding.recyclerView.adapter = adapter
    }

    private fun handlingLayoutManager(listUser: RecyclerView) {
        if (activity != null) {
            val resources = requireActivity().resources
            if (resources != null) {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    listUser.layoutManager = StaggeredGridLayoutManager(1,LinearLayoutManager.VERTICAL)
                } else {
                    listUser.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                }
            }
        }
    }


    /* Needs to be public for Databinding */
    fun onRefresh() {
        callAPI()
        setRecyclerView()
    }


}