package com.example.wakenmovie.data.remote

import com.example.wakenmovie.data.model.ServiceResponse
import com.example.wakenmovie.data.model.dto.MovieDto
import com.example.wakenmovie.data.model.dto.ResultsDto

interface MovieRepository {

    suspend fun getUpcomingMovies(page: Long): ServiceResponse<ResultsDto>
    suspend fun getMovie(movieId: Long): ServiceResponse<MovieDto>
    suspend fun getMovieByName(title: String): ServiceResponse<ResultsDto>
}