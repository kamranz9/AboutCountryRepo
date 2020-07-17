package com.assignment.country.view.activity

import android.os.Build
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.assignment.country.R
import com.assignment.country.view.adapter.AboutCanadaAdapter
import com.assignment.country.viewmodel.AboutCanadaViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class AboutCanadaFragmentTest {

    private lateinit var activity: AboutCanadaActivity
    private lateinit var aboutCanadaFragment: AboutCanadaFragment
    private lateinit var activityController: ActivityController<AboutCanadaActivity>
    private lateinit var mViewModel: AboutCanadaViewModel

    @Before
    fun setup() {

        activity = Robolectric.buildActivity(AboutCanadaActivity::class.java)
            .create()
            .resume()
            .get()

        aboutCanadaFragment = AboutCanadaFragment()
        activityController = Robolectric.buildActivity(AboutCanadaActivity::class.java)
        activityController.create().start().resume().visible()
        activityController.get()
            .supportFragmentManager
            .beginTransaction()
            .add(aboutCanadaFragment, null)
            .commit()
    }


    @Test
    @Throws(Exception::class)
    fun testClickEntry() {
        val recycler = aboutCanadaFragment.view!!.findViewById(R.id.recyclerView) as RecyclerView
        // workaround robolectric recyclerView issue
        recycler.measure(0,0)
        recycler.layout(0,0,100,1000)


        val adapter = AboutCanadaAdapter()
        mViewModel = ViewModelProvider(aboutCanadaFragment).get(AboutCanadaViewModel::class.java)
        mViewModel.data.observe(activity, Observer {
            it.let(adapter::submitList)
        })
        recycler.adapter = adapter

        // lets just imply a certain item at position 0 for simplicity
        recycler.findViewHolderForAdapterPosition(0)?.itemView?.performClick()

        // presenter is injected in my case, how this verification happens depends on how you manage your dependencies.
        //recycler.getChildAt(0).performClick()
    }


}