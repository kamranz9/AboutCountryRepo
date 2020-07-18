package com.assignment.country.view.activity

import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.assignment.country.R
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertNotNull
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
class AboutCanadaActivityTest {

    private lateinit var activity: AboutCanadaActivity
    private lateinit var fragmentTest: AboutCanadaFragment
    private lateinit var activityController: ActivityController<AboutCanadaActivity>

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(AboutCanadaActivity::class.java)
            .create()
            .resume()
            .get()


        fragmentTest = AboutCanadaFragment()
        activityController = Robolectric.buildActivity(AboutCanadaActivity::class.java)
        activityController.create().start().resume().visible()
        activityController.get()
            .supportFragmentManager
            .beginTransaction()
            .add(fragmentTest, null)
            .commit()


    }

    @After
    fun teardown() {
        // clean up after this class, leave nothing dirty behind
        stopKoin()
    }

    @Test
    @Throws(Exception::class)
    fun checkActivityNotNull() {
        assertNotNull(activity)
    }

    @Test
    fun checkFragmentShouldNotBeNull() {
        val loginFragment = AboutCanadaFragment()
        startFragment(loginFragment)
        assertNotNull(loginFragment)
    }

    private fun startFragment(fragment: Fragment) {
        val activity =
            Robolectric.buildActivity(FragmentActivity::class.java)
                .create()
                .start()
                .resume()
                .get()
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(fragment, null)
        fragmentTransaction.commit()
    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveCorrectAppName() {
        val appName = activity.resources.getString(R.string.app_name)
        assertThat(appName, equalTo("AboutCountryProject"))
    }

}