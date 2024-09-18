package com.platon.kotlinmirealabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.platon.kotlinmirealabs.databinding.FragmentSecondBinding
import com.platon.kotlinmirealabs.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)

        binding.button1.setOnClickListener {
            // Навигация к другому фрагменту с помощью NavController и указанием ID действия
            findNavController().navigate(R.id.action_thirdFragment_to_firstFragment)
        }

        binding.button2.setOnClickListener {
            // Навигация к другому фрагменту с помощью NavController и указанием ID действия
            findNavController().navigate(R.id.action_thirdFragment_to_secondFragment)
        }


        return binding.root
    }
}