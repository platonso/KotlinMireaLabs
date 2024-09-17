package com.platon.kotlinmirealabs

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.platon.kotlinmirealabs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Проверяем, что первый запуск и сохранённого состояния
        if (savedInstanceState == null) {
            replaceFragment(FirstFragment())
        }

        binding.button1.setOnClickListener {
            replaceFragment(FirstFragment())
        }
        binding.button2.setOnClickListener {
            replaceFragment(SecondFragment())
        }
        binding.button3.setOnClickListener {
            replaceFragment(ThirdFragment())
        }
    }

    private fun replaceFragment(newFragment: Fragment) { // Ручное управление транзакцией
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, newFragment)
            .addToBackStack(null)
            .commit()

    }
}