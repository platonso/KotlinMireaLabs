package com.platon.kotlinmirealabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.braintreepayments.cardform.view.CardForm
import com.platon.kotlinmirealabs.ViewModels.SecondViewModel
import com.platon.kotlinmirealabs.databinding.FragmentFirstBinding
import com.platon.kotlinmirealabs.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SecondViewModel::class.java)

        viewModel.cardHolderName.observe(viewLifecycleOwner) {
            binding.cardHolderNameInput.setText(it)
        }

        viewModel.cardNumber.observe(viewLifecycleOwner) {
            binding.cardNumberInput.setText(it)
        }

        viewModel.expDate.observe(viewLifecycleOwner) {
            binding.expDateInput.setText(it)
        }

        viewModel.cvv.observe(viewLifecycleOwner) {
            binding.cvvInput.setText(it)
        }


        binding.saveButton2.setOnClickListener {
            val cardHolderName = binding.cardHolderNameInput.text.toString().trim()
            val cardNumber = binding.cardNumberInput.text.toString()
            val expDate = binding.expDateInput.text.toString()
            val cvv = binding.cvvInput.text.toString()

            viewModel.setCardHolderName(cardHolderName)
            viewModel.setCardNumber(cardNumber)
            viewModel.setExpDate(expDate)
            viewModel.setCvv(cvv)
        }

    }
}