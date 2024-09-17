package com.platon.kotlinmirealabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.platon.kotlinmirealabs.ViewModels.FirstViewModel
import com.platon.kotlinmirealabs.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: FirstViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(FirstViewModel::class.java)


        viewModel.name.observe(viewLifecycleOwner) {
            binding.input1.setText(it)
        }

        viewModel.surname.observe(viewLifecycleOwner) {
            binding.input2.setText(it)
        }

        binding.saveButton1.setOnClickListener {
            val name = binding.input1.getText().toString().trim()
            val surname = binding.input2.getText().toString().trim()

            viewModel.setName(name)
            viewModel.setSurname(surname)
        }

    }
}
