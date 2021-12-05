package com.zenjob.android.browsr.network

import com.zenjob.android.browsr.BuildConfig
import com.zenjob.android.browsr.model.PaginatedListResponse
import retrofit2.Response
import javax.inject.Inject



class MovieRepositoryImpl @Inject constructor(private val movieService: MovieService) :
    MovieRepository{
    override suspend fun getAllMovies(pageNumber:Int?): Response<PaginatedListResponse> =
        movieService.getPopularTvShows(BuildConfig.TMDB_API_KEY,"en-US",pageNumber?:1)
}