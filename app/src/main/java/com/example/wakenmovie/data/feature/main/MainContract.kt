package com.example.wakenmovie.data.feature.main

import com.example.wakenmovie.base.BasePresenter
import com.example.wakenmovie.base.BaseView
import com.example.wakenmovie.data.model.dto.SimpleMovieDto

interface MainContract {
    interface View : BaseView<Presenter> {
        fun showLoadingMovies()
        fun hideLoadingMovies()
        fun onSuccessfulLoadMovies(movies: List<SimpleMovieDto>)
        fun onSuccessfullLoadByTitle(movies: List<SimpleMovieDto>)
        fun onFailureLoadMovies(message: String)
    }

    interface Presenter : BasePresenter<View> {
        fun loadMovies(page: Long)
        fun loadMoviesByTitle(movieTitle: String)
    }
}
