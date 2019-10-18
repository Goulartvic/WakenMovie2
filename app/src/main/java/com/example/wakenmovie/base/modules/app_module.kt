package com.example.wakenmovie.base.modules

import com.google.gson.GsonBuilder
import com.example.wakenmovie.data.feature.main.MainContract
import com.example.wakenmovie.data.feature.main.MainPresenter
import com.example.wakenmovie.data.feature.movie.MovieContract
import com.example.wakenmovie.data.feature.movie.MoviePresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Main
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    factory { (view: MainContract.View) ->
        MainPresenter(
            view = view,
            movieRepository = get(),
            dispatcherContext = get()
        )
    } bind MainContract.Presenter::class

    factory { (view: MovieContract.View) ->
        MoviePresenter(
            view = view,
            movieRepository = get(),
            dispatcherContext = get()
        )
    } bind MovieContract.Presenter::class
}

val featureModule = module {
    single {
        GsonConverterFactory.create(GsonBuilder().setLenient().create())
    } bind Converter.Factory::class
}

val dispatcherModule = module {
    factory { Main as CoroutineDispatcher }
}