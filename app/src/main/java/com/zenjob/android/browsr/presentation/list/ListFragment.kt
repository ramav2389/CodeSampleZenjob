package com.zenjob.android.browsr.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.zenjob.android.browsr.databinding.FragmentListBinding
import com.zenjob.android.browsr.databinding.ViewholderMovieItemBinding
import com.zenjob.android.browsr.model.Movie
import com.zenjob.android.browsr.util.LoadStateAdapterForList
import com.zenjob.android.browsr.util.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment(), MovieListAdapter.ItemClickListener {

    @Inject
    lateinit var mAdapter: MovieListAdapter
    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentListBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * we can also handle all the actions in Kotlin flow with more scalable interfaces due to time
         * concern i am not using actions and uistate
         */
        binding.swipeToRefreshMovies.setOnRefreshListener {
            binding.swipeToRefreshMovies.isRefreshing = false
        }
        binding.list.layoutManager = GridLayoutManager(binding.list.context, 2)
        binding.list.adapter = mAdapter
        mAdapter.listener = this@ListFragment
        observeMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    private fun observeMovies() {
        mAdapter.withLoadStateFooter(footer = LoadStateAdapterForList { mAdapter.retry() })
        lifecycleScope.launch {
            viewModel.movies.collectLatest {
                mAdapter.submitData(it)
            }
        }
        mAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    it.error.message?.let { it1 -> binding.root.snack(it1) }
                }
            }

        }

    }

    override fun onMovieClicked(binding: ViewholderMovieItemBinding, movie: Movie) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(movie))
    }
}