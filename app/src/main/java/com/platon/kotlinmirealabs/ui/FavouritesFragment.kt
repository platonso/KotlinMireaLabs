package com.platon.kotlinmirealabs.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.platon.kotlinmirealabs.R
import com.platon.kotlinmirealabs.adapters.NewsAdapter
import com.platon.kotlinmirealabs.databinding.FragmentFavouritesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var newsAdapter: NewsAdapter
    private val newsViewModel: NewsViewModel by viewModel() // Получаем ViewModel через Koin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouritesBinding.bind(view)
        setupFavouritesRecycler()

        newsViewModel.getFavoriteNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.differ.submitList(articles)
        })
    }

    private fun setupFavouritesRecycler() {
        newsAdapter = NewsAdapter()
        binding.recyclerFavourites.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
