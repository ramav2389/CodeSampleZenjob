package com.zenjob.android.browsr.presentation.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.zenjob.android.browsr.base.BaseCoroutinesViewModel
import com.zenjob.android.browsr.model.Movie
import com.zenjob.android.browsr.model.PaginatedListResponse
import com.zenjob.android.browsr.network.MovieRepository
import com.zenjob.android.browsr.util.PaginationSourceForMovie
import com.zenjob.android.browsr.util.Resource
import com.zenjob.android.browsr.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(private val repository: MovieRepository) :
    BaseCoroutinesViewModel(application = Application()) {
    val movies: Flow<PagingData<Movie>> = getMovieListStream()

    private fun getMovieListStream(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(20)) {
            PaginationSourceForMovie(repository)
        }.flow
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}