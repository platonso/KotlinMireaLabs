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

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .cardholderName(CardForm.FIELD_REQUIRED)
            .setup(requireActivity())


        binding.button1.setOnClickListener {
            // Навигация к другому фрагменту с помощью NavController и указанием ID действия
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }

        binding.button3.setOnClickListener {
            // Навигация к другому фрагменту с помощью NavController и указанием ID действия
            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
        }

        return binding.root
    }

}