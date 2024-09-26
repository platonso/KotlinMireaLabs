package com.platon.kotlinmirealabs

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScaffold()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold() {
    var selectedScreen by remember { mutableStateOf(Screen.Home) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = selectedScreen.title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                BottomNavigation(selectedScreen) { selectedScreen = it }
            }
        },
        content = { padding ->
            when (selectedScreen) {
                Screen.Home -> HomeScreen(padding)
                Screen.Image -> ImageDownloadScreen(padding)
                Screen.Settings -> SettingsScreen(padding)
            }
        }
    )
}

@Composable
fun BottomNavigation(selectedScreen: Screen, onScreenSelected: (Screen) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedScreen == Screen.Home,
            onClick = { onScreenSelected(Screen.Home) },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) }
        )
        NavigationBarItem(
            selected = selectedScreen == Screen.Image,
            onClick = { onScreenSelected(Screen.Image) },
            label = { Text("Image") },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = null) }
        )
        NavigationBarItem(
            selected = selectedScreen == Screen.Settings,
            onClick = { onScreenSelected(Screen.Settings) },
            label = { Text("Settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) }
        )
    }
}

@Composable
fun HomeScreen(padding: PaddingValues) {
    Text(
        text = "Home Screen",
        modifier = Modifier.padding(padding)
    )
}

@Composable
fun ImageDownloadScreen(padding: PaddingValues) {
    val context = LocalContext.current
    var imageUrl by remember { mutableStateOf(TextFieldValue()) }
    var workInfo by remember { mutableStateOf<WorkInfo?>(null) }

    val workManager = WorkManager.getInstance(context)

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        workInfo?.let {
            when (it.state) {
                WorkInfo.State.SUCCEEDED -> {
                    Toast.makeText(context, "Изображение сохранено", Toast.LENGTH_SHORT).show()
                }
                WorkInfo.State.FAILED -> {
                    Toast.makeText(context, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
                }
                WorkInfo.State.RUNNING -> {
                    CircularProgressIndicator()
                }
                else -> {}
            }
        }

        TextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Ввести ссылку") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (imageUrl.text.isNotEmpty()) {
                val data = workDataOf("imageUrl" to imageUrl.text)

                val downloadRequest = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
                    .setInputData(data)
                    .build()

                workManager.enqueueUniqueWork(
                    "imageDownload",
                    ExistingWorkPolicy.REPLACE,
                    downloadRequest
                )
            }
        }) {
            Text("Загрузить изображение")
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}



@Composable
fun SettingsScreen(padding: PaddingValues) {
    Text(
        text = "Settings Screen",
        modifier = Modifier.padding(padding)
    )
}

enum class Screen(val title: String) {
    Home("Home"),
    Image("Image Download"),
    Settings("Settings")
}
