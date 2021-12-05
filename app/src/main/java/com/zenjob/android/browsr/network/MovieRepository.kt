package com.zenjob.android.browsr.network

import com.zenjob.android.browsr.model.PaginatedListResponse
import retrofit2.Response


interface MovieRepository {
    suspend fun getAllMovies(pageNumber: Int?): Response<PaginatedListResponse>
}