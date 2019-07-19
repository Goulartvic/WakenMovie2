package com.example.wakenmovie.data.feature.main

import android.os.Bundle
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tanheta.wakenmovie.R
import com.example.wakenmovie.base.extensions.isVisible
import com.example.wakenmovie.base.extensions.showToast
import com.example.wakenmovie.data.feature.movie.MovieActivity
import com.example.wakenmovie.data.model.dto.SimpleMovieDto
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainContract.View {
    override val presenter by inject<MainContract.Presenter> {parametersOf(this)}

    private val adapter by lazy { MovieAdapter() }

    private var isScrolling = false
    private var page: Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        presenter.loadMovies(page)
    }

    override fun showLoadingMovies() {
        mainViewLoading.isVisible = true
    }

    override fun hideLoadingMovies() {
        mainViewLoading.isVisible = false
    }

    override fun onSuccessfulLoadMovies(movies: List<SimpleMovieDto>) {
        val auxiliarList: MutableList<SimpleMovieDto> = mutableListOf()
        auxiliarList.addAll(adapter.data)
        auxiliarList.addAll(movies)
        adapter.data = auxiliarList
    }

    override fun onFailureLoadMovies(message: String) {
        showToast(message)
    }

    private fun setupViews() {
        setSupportActionBar(mainToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mainToolbar.tmTxtTitle.setText(R.string.app_name)

        mainRecMovies.layoutManager = GridLayoutManager(this, 2)
        setupScrollListener(mainRecMovies)
        mainRecMovies.adapter = adapter
        adapter.setOnItemClickListener {
            MovieActivity.startMovieActivity(this, it.id)
        }
    }

    private fun setupScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val gridLayoutManager = recyclerView
                    .layoutManager as GridLayoutManager?
                val currentItems = gridLayoutManager?.childCount
                val totalItems = gridLayoutManager?.itemCount
                val scrollOutItems = gridLayoutManager?.findFirstVisibleItemPosition()
                val expectedTotal = scrollOutItems?.let { currentItems?.plus(it) }
                if (isScrolling && expectedTotal == totalItems) {
                    isScrolling = false
                    page = page.plus(1)
                    presenter.loadMovies(page)
                }
            }
        })
    }
}