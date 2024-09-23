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

class HeadLineFragment : Fragment(R.layout.fragment_headline) {

    private lateinit var binding: FragmentHeadlineBinding
    lateinit var newsAdapter: NewsAdapter
    lateinit var newsViewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlineBinding.bind(view)

        newsViewModel = (activity as MainActivity).newsViewModel
        setupHeadlinesRecycler()




        newsViewModel.headlines.observe(
            viewLifecycleOwner
        ) { response ->
            response.data?.let { newsResponse ->
                newsAdapter.differ.submitList(newsResponse.articles.toList())
            }
        }

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ){

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