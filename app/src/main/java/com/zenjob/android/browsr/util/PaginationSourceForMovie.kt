package com.zenjob.android.browsr.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zenjob.android.browsr.model.Movie
import com.zenjob.android.browsr.network.MovieRepository


class PaginationSourceForMovie(private val movieRepository: MovieRepository) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = movieRepository.getAllMovies(nextPage)

            LoadResult.Page(
                data = movieListResponse.body()?.results!!,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < movieListResponse.body()?.totalPages!!)
                    movieListResponse.body()?.page?.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}