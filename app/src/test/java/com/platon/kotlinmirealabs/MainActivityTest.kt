package com.platon.kotlinmirealabs

import android.content.Context
import android.graphics.Bitmap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.DEFAULT_MANIFEST_NAME)

class MainActivityTest {
    private lateinit var mockContext: Context
    private lateinit var mainActivity: MainActivity
    private lateinit var spyMainActivity: MainActivity
    private val testDispatcher = StandardTestDispatcher()
    private val input: EditText = mock(EditText::class.java)
    private val button: Button = mock(Button::class.java)
    private val imageView: ImageView = mock(ImageView::class.java)

    @Before
    fun setUp() {
        mockContext = mock(Context::class.java)
        Dispatchers.setMain(testDispatcher)
        mainActivity = MainActivity()
        spyMainActivity = spy(MainActivity())
        spyMainActivity.input = input
        spyMainActivity.button = button
        spyMainActivity.imageView = imageView
    }

    @Test
    // Проверка создания MainActivity
    fun testMainActivityCreation() {
        assertNotNull(mainActivity)
    }

    @Test
    // Проверка вызова метода downloadAndSaveImage при нажатии на кнопку
    fun downloadAndSaveImage() {
        val imageUrl =
            "https://i.pinimg.com/originals/c3/35/48/c33548ed5b34b865e26f954b86b3e915.jpg"
        Mockito.doAnswer {
            spyMainActivity.downloadAndSaveImage(imageUrl, spyMainActivity)
            null
        }.`when`(button).performClick()
        button.performClick()
        verify(spyMainActivity).downloadAndSaveImage(imageUrl, spyMainActivity)
    }

    // Проверка на успешное скачивание изображения по заданному URL
    @Test
    fun testDownloadImageSuccess() = runBlocking {
        val imageUrl =
            "https://i.pinimg.com/originals/c3/35/48/c33548ed5b34b865e26f954b86b3e915.jpg"
        val bitmapDeferred = mainActivity.downloadImage(imageUrl)
        val bitmap = bitmapDeferred.await()
        assertNotNull(bitmap)
    }
    // Проверка сценария, когда скачивание изображения по неверному URL не удаётся
    @Test
    fun testDownloadImageFailure() = runBlocking {
        val invalidImageUrl = "https://invalid.jpg"
        val bitmapDeferred = mainActivity.downloadImage(invalidImageUrl)
        val bitmap = bitmapDeferred.await()
        assertNull(bitmap)
    }

    // Проверка, что метод compress вызывается на объекте Bitmap при сохранении изображения на диск
    @Test
    fun saveImageToDisk() = runTest {
        val mainActivity: MainActivity = mock()
        val bitmap = mock(Bitmap::class.java)
        val context = mock(Context::class.java)
        val spiedMainActivity = spy(mainActivity)
        spiedMainActivity.saveImageToDisk(bitmap, context).join()
        verify(bitmap).compress(eq(Bitmap.CompressFormat.JPEG), eq(100), Mockito.any())
    }
}