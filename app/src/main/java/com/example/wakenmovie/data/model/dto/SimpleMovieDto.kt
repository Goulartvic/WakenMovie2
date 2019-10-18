package com.example.wakenmovie.data.model.dto

import com.google.gson.annotations.SerializedName

data class SimpleMovieDto(
    val id: Long,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)