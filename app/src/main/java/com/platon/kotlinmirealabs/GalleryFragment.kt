package com.platon.kotlinmirealabs

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.platon.kotlinmirealabs.databinding.FragmentCameraBinding
import com.platon.kotlinmirealabs.databinding.FragmentGalleryBinding
import java.io.File
import java.io.FileInputStream

class GalleryFragment: Fragment() {

    private lateinit var binding: FragmentGalleryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recyclerView
        val photoDateList = readPhotoDates()
        val adapter = DateAdapter(photoDateList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    fun readPhotoDates(): List<String> {
        val photoDates = mutableListOf<String>()
        val photosDir = File(requireContext()
            .getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photos")
        val dateFile = File(photosDir, "date.txt")

        if (dateFile.exists()) {
            try {
                FileInputStream(dateFile).use { inputStream ->
                    inputStream.bufferedReader().use { reader ->
                        reader.forEachLine { line ->
                            photoDates.add(line)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return photoDates
    }
}