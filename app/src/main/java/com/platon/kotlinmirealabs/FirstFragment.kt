package com.platon.kotlinmirealabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.braintreepayments.cardform.view.CardForm
import com.platon.kotlinmirealabs.databinding.FragmentFirstBinding
import com.platon.kotlinmirealabs.databinding.FragmentSecondBinding

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.button2.setOnClickListener {
            // Навигация к другому фрагменту с помощью NavController и указанием ID действия
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }

        binding.button3.setOnClickListener {
            // Навигация к другому фрагменту с помощью NavController и указанием ID действия
            findNavController().navigate(R.id.action_firstFragment_to_thirdFragment)
        }
        return binding.root
    }

}