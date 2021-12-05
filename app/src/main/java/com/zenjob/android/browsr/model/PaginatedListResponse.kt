package com.zenjob.android.browsr.model

import com.google.gson.annotations.SerializedName
import com.zenjob.android.browsr.model.Movie


data class PaginatedListResponse(
    val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<Movie>
)
