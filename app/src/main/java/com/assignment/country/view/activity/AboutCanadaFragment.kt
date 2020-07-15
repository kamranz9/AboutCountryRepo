package com.assignment.country.view.activity

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.assignment.country.BuildConfig
import com.assignment.country.R
import com.assignment.country.databinding.FragmentAboutcanadaBinding
import com.assignment.country.helper.DividerItemDecoration
import com.assignment.country.view.adapter.AboutCanadaAdapter
import com.assignment.country.viewmodel.AboutCanadaViewModel
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Single
import org.koin.android.viewmodel.ext.android.viewModel


class AboutCanadaFragment : Fragment() {

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
            mViewModel.userRecycler?.set(View.VISIBLE)
            mViewModel.userLabel?.set(View.GONE)
            mViewModel.messageLabel?.set("")
            callAPI()
        } else {
            mViewModel.messageLabel?.set(requireContext().getString(R.string.error_message_loading_internet))
            mViewModel.userLabel?.set(View.VISIBLE)
            mViewModel.userRecycler?.set(View.GONE)
        }

        setRecyclerView()
        callSwipe()

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

    private fun dispatchFailure(error: Throwable?) {
        error?.let {
            if (BuildConfig.DEBUG) {
                it.printStackTrace()
            }
            //Toast.makeText(context, it.message, length).show()
            mViewModel.messageLabel?.set(requireContext().getString(R.string.error_message_loading_users))
            mViewModel.userLabel?.set(View.VISIBLE)
            mViewModel.userRecycler?.set(View.GONE)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(1,LinearLayoutManager.VERTICAL)
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        }
        mBinding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner,Lifecycle.Event.ON_DESTROY)))

    private fun setRecyclerView() {
        val adapter = AboutCanadaAdapter()
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            it.let(adapter::submitList)
        })
        handlingLayoutManager(mBinding.recyclerView)
        mBinding.recyclerView.addItemDecoration(DividerItemDecoration(context,R.drawable.divider))
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


    /**
     * Contains Refresh listener for Pull to Refresh Functionality
     */
    private fun callSwipe(){
        mBinding.swipeContainer.setOnRefreshListener {

            if (isInternetAvailable()) {
                mViewModel.isLoading.set(true)
                mViewModel.userRecycler?.set(View.VISIBLE)
                mViewModel.userLabel?.set(View.GONE)
                mViewModel.messageLabel?.set("")
                callAPI()
            } else {
                mViewModel.isLoading.set(false)
                mViewModel.messageLabel?.set(requireContext().getString(R.string.error_message_loading_internet))
                mViewModel.userLabel?.set(View.VISIBLE)
                mViewModel.userRecycler?.set(View.GONE)
            }

            mBinding.recyclerView.adapter?.notifyDataSetChanged()
        }

    }


}