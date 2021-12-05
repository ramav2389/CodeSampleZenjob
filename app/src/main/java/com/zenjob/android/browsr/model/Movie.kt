package com.zenjob.android.browsr.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Movie(
    val id: Long,
    val imdbId: String?,
    val overview: String?,
    val title: String,
    @SerializedName("poster_path") val image: String?,
    @SerializedName("release_date") val releaseDate: Date?,
    @SerializedName("vote_average") val voteAverage: Float?
) : Serializable
