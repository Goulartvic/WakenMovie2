package com.example.wakenmovie.data.feature.movie

import com.example.wakenmovie.base.extensions.launch
import com.example.wakenmovie.data.model.whenever
import com.example.wakenmovie.data.remote.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher

class MoviePresenter(
    override var view: MovieContract.View,
    private val movieRepository: MovieRepository,
    private val dispatcherContext: CoroutineDispatcher
) : MovieContract.Presenter {
    override fun loadMovie(gameId: Long) {
        view.showLoadingMovies()

        dispatcherContext.launch {
            movieRepository.getMovie(gameId).whenever(
                isBody = {
                    view.hideLoadingMovies()
                    view.onSuccessfulLoadMovie(it)
                },
                isError = {
                    view.hideLoadingMovies()
                    view.onFailuereLoadMovie(it)
                }
            )
        }
    }

}