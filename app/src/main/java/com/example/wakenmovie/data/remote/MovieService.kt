package com.example.wakenmovie.data.remote

import com.example.wakenmovie.base.extensions.awaitResult
import com.example.wakenmovie.data.model.ServiceResponse
import com.example.wakenmovie.data.model.dto.MovieDto
import com.example.wakenmovie.data.model.dto.ResultsDto
import com.example.wakenmovie.data.remote.MovieApi
import com.example.wakenmovie.data.remote.MovieRepository
import kotlinx.coroutines.delay

class MovieService(private val api: MovieApi) : MovieRepository {
    override suspend fun getUpcomingMovies(page: Long): ServiceResponse<ResultsDto> {
        return api.getUpcomingMovies(page).awaitResult()
    }

    override suspend fun getMovie(movieId: Long): ServiceResponse<MovieDto> {
        delay(2200)

        return api.getMovie(movieId).awaitResult()
    }

    override suspend fun getMovieByName(title: String): ServiceResponse<ResultsDto> {
        return api.getMoviesByName(title).awaitResult()
    }
}