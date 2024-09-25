package com.platon.kotlinmirealabs

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // Проверка видимости основных элементов UI
    @Test
    fun testUIElementsVisibility() {
        onView(withId(R.id.input)).check(matches(isDisplayed()))
        onView(withId(R.id.button)).check(matches(isDisplayed()))
    }

    // Проверка состояния UI, если поле ввода пустое
    @Test
    fun testEmptyInputUIState() {
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.input)).check(matches(withText("")))
        onView(withId(R.id.button)).check(matches(isEnabled()))
    }

    // Проверка ввода текста в TextInputEditText
    @Test
    fun testTextInput() {
        val testUrl = "https://example.com/image.jpg"
        onView(withId(R.id.input)).perform(typeText(testUrl), closeSoftKeyboard())
        onView(withId(R.id.input)).check(matches(withText(testUrl)))
    }

    // Проверка загрузки изображения
    @Test
    fun testImageLoading() {
        val testUrl = "https://i.pinimg.com/originals/c3/35/48/c33548ed5b34b865e26f954b86b3e915.jpg"
        onView(withId(R.id.input)).perform(typeText(testUrl), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    // Проверка изначального значения поля ввода
    @Test
    fun checkEditTextInitialState() {
        onView(withId(R.id.input)).check(matches(withText("")))
    }
}
