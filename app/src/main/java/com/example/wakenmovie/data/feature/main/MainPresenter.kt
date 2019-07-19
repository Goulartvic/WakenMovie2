package com.example.wakenmovie.data.feature.main

import com.example.wakenmovie.base.extensions.launch
import com.example.wakenmovie.data.model.whenever
import com.example.wakenmovie.data.remote.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher

class MainPresenter(
    override var view: MainContract.View,
    private val movieRepository: MovieRepository,
    private val dispatcherContext: CoroutineDispatcher
) : MainContract.Presenter {
    override fun loadMovies(page: Long) {
        view.showLoadingMovies()

        dispatcherContext.launch {
            movieRepository.getUpcomingMovies(page).whenever(
                isBody = {
                    view.hideLoadingMovies()
                    view.onSuccessfulLoadMovies(it.results)
                },
                isError = {
                    view.hideLoadingMovies()
                    view.onFailureLoadMovies(it)
                }
            )
        }
    }

    override fun loadMoviesByTitle(movieTitle: String) {
        view.showLoadingMovies()

        dispatcherContext.launch {
            movieRepository.getMovieByName(movieTitle).whenever(
                isBody = {
                    view.hideLoadingMovies()
                    view.onSuccessfullLoadByTitle(it.results)
                },
                isError = {
                    view.hideLoadingMovies()
                    view.onFailureLoadMovies(it)
                }
            )
        }
    }

}