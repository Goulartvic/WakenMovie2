package com.example.wakenmovie.data.feature.movie

import com.example.wakenmovie.base.BasePresenter
import com.example.wakenmovie.base.BaseView
import com.example.wakenmovie.data.model.dto.MovieDto

interface MovieContract {
    interface View : BaseView<Presenter> {
        fun showLoadingMovies()
        fun hideLoadingMovies()
        fun onSuccessfulLoadMovie(movie: MovieDto)
        fun onFailuereLoadMovie(message: String)
    }

    interface Presenter : BasePresenter<View> {
        fun loadMovie(gameId: Long)
    }
}