package com.example.wakenmovie.base

interface BaseView<out T : BasePresenter<*>> {
    val presenter: T
}