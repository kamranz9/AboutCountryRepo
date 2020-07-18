package com.assignment.country.view.activity

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.assignment.country.R
import com.assignment.country.model.data.RowEntity
import com.assignment.country.view.adapter.AboutCanadaAdapter
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
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
    private lateinit var mockList: List<RowEntity>

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

        mockList = listOf(
            RowEntity(
                "title1",
                "description1",
                "https://images.app.goo.gl/UWsKdhYbsLZLKz129"
            )
        )
    }


    @After
    fun teardown() {
        // clean up after this class, leave nothing dirty behind
        stopKoin()
    }


    @Test
    @Throws(Exception::class)
    fun testViewHolder() {
        val recycler = aboutCanadaFragment.view!!.findViewById(R.id.recyclerView) as RecyclerView
        // workaround robolectric recyclerView issue
        recycler.measure(0, 0)
        recycler.layout(0, 0, 100, 1000)

        val adapter = AboutCanadaAdapter()
        mockList.let(adapter::submitList)
        recycler.adapter = adapter
        recycler.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
    }

    @Test
    @Throws(Exception::class)
    fun testTitleAtFirstPosition() {
        val recycler = aboutCanadaFragment.view!!.findViewById(R.id.recyclerView) as RecyclerView
        // workaround robolectric recyclerView issue
        recycler.measure(0, 0)
        recycler.layout(0, 0, 100, 1000)
        recycler.layoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        val adapter = AboutCanadaAdapter()
        mockList.let(adapter::submitList)
        recycler.adapter = adapter


        recycler.doOnPreDraw {
            val findViewByPosition: View? = recycler.layoutManager?.findViewByPosition(0)
            val title = findViewByPosition?.findViewById(R.id.label_title) as TextView
            assertThat(title.text.toString(), Matchers.equalTo("title1"))
        }
    }

    @Test
    @Throws(Exception::class)
    fun testRecyclerAdapterNotNull() {
        val recycler = aboutCanadaFragment.view!!.findViewById(R.id.recyclerView) as RecyclerView
        // workaround robolectric recyclerView issue
        recycler.measure(0, 0)
        recycler.layout(0, 0, 100, 1000)
        val adapter = AboutCanadaAdapter()
        mockList.let(adapter::submitList)
        recycler.adapter = adapter
        assertNotNull(recycler.adapter)
    }


}