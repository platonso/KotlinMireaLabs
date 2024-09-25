package com.platon.kotlinmirealabs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.platon.kotlinmirealabs.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var input: EditText
    lateinit var button: Button
    lateinit var imageView: ImageView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        input = findViewById(R.id.input)
        button= findViewById(R.id.button)
        imageView = findViewById(R.id.imageView)

        button.setOnClickListener {
            val imageUrl = input.text.toString()
            if (imageUrl.isNotEmpty()) {
                downloadAndSaveImage(imageUrl, this)
            } else {
                showToast("Введите ссылку на изображение")
            }
        }
    }

    fun downloadAndSaveImage(imageUrl: String, context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            val bitmapDeferred = downloadImage(imageUrl) // Network Thread
            val bitmap = bitmapDeferred.await()
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                launch(Dispatchers.IO) {
                    saveImageToDisk(bitmap, context) // Disk Thread
                }
            } else {
                showToast("Ошибка загрузки изображения")
            }
        }
    }

    fun downloadImage(imageUrl: String): Deferred<Bitmap?> {
        return CoroutineScope(Dispatchers.IO).async{
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

    suspend fun saveImageToDisk(bitmap: Bitmap, context: Context): Job {
        return CoroutineScope(Dispatchers.IO).launch {
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
                    showToast("Изображение сохранено")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    showToast("Ошибка сохранения изображения")
                }
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
