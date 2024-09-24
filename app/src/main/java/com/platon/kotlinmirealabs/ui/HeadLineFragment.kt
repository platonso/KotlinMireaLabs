package com.platon.kotlinmirealabs.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.platon.kotlinmirealabs.R
import com.platon.kotlinmirealabs.adapters.NewsAdapter
import com.platon.kotlinmirealabs.databinding.FragmentHeadlineBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeadLineFragment : Fragment(R.layout.fragment_headline) {

    private lateinit var binding: FragmentHeadlineBinding
    private lateinit var newsAdapter: NewsAdapter
    private val newsViewModel: NewsViewModel by viewModel() // Используем Koin для получения ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlineBinding.bind(view)
        setupHeadlinesRecycler()

        // Наблюдаем за новостями
        newsViewModel.headlines.observe(viewLifecycleOwner) { response ->
            response.data?.let { newsResponse ->
                newsAdapter.differ.submitList(newsResponse.articles.toList())
            }
        }

        // Настраиваем ItemTouchHelper для обработки свайпов
        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val article = newsAdapter.differ.currentList[position]
                    newsViewModel.addToFavorites(article)
                }
            }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerHeadlines)
        }
    }

    private fun setupHeadlinesRecycler() {
        newsAdapter = NewsAdapter()
        binding.recyclerHeadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
