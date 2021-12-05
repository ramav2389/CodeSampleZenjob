package com.zenjob.android.browsr.network

import com.zenjob.android.browsr.model.Movie
import com.zenjob.android.browsr.model.PaginatedListResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") api: String? = null,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): Response<PaginatedListResponse>


    @GET("movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") query: String? = null
    ): Single<Movie>
}