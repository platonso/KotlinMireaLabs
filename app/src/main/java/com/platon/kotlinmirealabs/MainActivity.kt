package com.platon.kotlinmirealabs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.platon.kotlinmirealabs.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.button.setOnClickListener {
            val imageUrl = binding.input.text.toString()
            if (imageUrl.isNotEmpty()) {
                downloadAndSaveImage(imageUrl, this)
            } else {
                Toast.makeText(this, "Введите ссылку на изображение",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadAndSaveImage(imageUrl: String, context: Context) {
        lifecycleScope.launch {
            val bitmap = downloadImage(imageUrl) // Network Thread
            if (bitmap != null) {
                binding.imageView.setImageBitmap(bitmap)
                // Запускаем сохранение в отдельной корутине
                launch(Dispatchers.IO) {
                    saveImageToDisk(bitmap, context) // Disk Thread
                }
            } else {
                Toast.makeText(context, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private suspend fun downloadImage(imageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection()
                connection.doInput = true
                connection.connect()
                val input = connection.getInputStream()
                BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private suspend fun saveImageToDisk(bitmap: Bitmap, context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val file = File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "downloaded_image.jpg"
                )
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Изображение сохранено", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Ошибка сохранения изображения", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}