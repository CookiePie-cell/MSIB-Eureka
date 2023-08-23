package com.salugan.githubuser

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.salugan.githubuser.ui.activities.main.MainActivity
import com.salugan.githubuser.ui.activities.splash.SplashActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testMainActivityIsDisplayedAfterSplashScreen() {
        onView(withId(R.id.splash_screen)).check(doesNotExist())

        onView(withId(R.id.activity_main)).check(matches(isDisplayed()))
    }
}