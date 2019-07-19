package com.example.wakenmovie.data.model.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val id: Long,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("release_date")
    val releaseDate: String
)