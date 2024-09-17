package com.platon.kotlinmirealabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.platon.kotlinmirealabs.ViewModels.ThirdViewModel
import com.platon.kotlinmirealabs.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding
    private lateinit var viewModel: ThirdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ThirdViewModel::class.java)


        // Подписка на изменения данных в ViewModel и установка состояний переключателей
        viewModel.notificationsEnabled.observe(viewLifecycleOwner) { isChecked ->
            binding.switch1.isChecked = isChecked
        }
        viewModel.darkThemeEnabled.observe(viewLifecycleOwner) { isChecked ->
            binding.switch2.isChecked = isChecked
        }
        viewModel.batterySaverEnabled.observe(viewLifecycleOwner) { isChecked ->
            binding.switch3.isChecked = isChecked
        }
        viewModel.autoUpdateEnabled.observe(viewLifecycleOwner) { isChecked ->
            binding.switch4.isChecked = isChecked
        }
        viewModel.backupEnabled.observe(viewLifecycleOwner) { isChecked ->
            binding.switch5.isChecked = isChecked
        }
        viewModel.vibrationEnabled.observe(viewLifecycleOwner) { isChecked ->
            binding.switch6.isChecked = isChecked
        }
        viewModel.analyticsEnabled.observe(viewLifecycleOwner) { isChecked ->
            binding.switch7.isChecked = isChecked
        }

        // Установка слушателей на переключатели
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationEnabled(isChecked)
        }
        binding.switch2.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkThemeEnabled(isChecked)
        }
        binding.switch3.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setBatterySaverEnabled(isChecked)
        }
        binding.switch4.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setAutoUpdateEnabled(isChecked)
        }
        binding.switch5.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setBackupEnabled(isChecked)
        }
        binding.switch6.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setVibrationEnabled(isChecked)
        }
        binding.switch7.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setAnalyticsEnabled(isChecked)
        }


    }
}