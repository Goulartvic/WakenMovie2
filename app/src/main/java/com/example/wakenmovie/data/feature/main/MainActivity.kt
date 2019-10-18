package com.example.wakenmovie.data.feature.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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
    private var isSearching = false
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

    override fun onSuccessfullLoadByTitle(movies: List<SimpleMovieDto>) {
        adapter.data = movies
    }

    override fun onFailureLoadMovies(message: String) {
        showToast(message)
    }

    private fun setupViews() {
        setSupportActionBar(mainToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mainToolbar.tmTxtTitle.setText(R.string.movies)
        mainToolbar.tmImgSearch.setOnClickListener {
            mainToolbar.tmSearchText.visibility = View.VISIBLE
            mainToolbar.tmImgSearch.visibility = View.GONE
            isSearching = true
        }

        addToolbarTextChangeListener()

        mainRecMovies.layoutManager = GridLayoutManager(this, 2)
        setupScrollListener(mainRecMovies)
        mainRecMovies.adapter = adapter
        adapter.setOnItemClickListener {
            MovieActivity.startMovieActivity(this, it.id)
        }
    }

    private fun addToolbarTextChangeListener() {
        mainToolbar.tmSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isEmpty()) {
                    isSearching = false
                    page = 1
                    adapter.data = emptyList()
                    presenter.loadMovies(page)
                    return
                }
                presenter.loadMoviesByTitle(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
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
                if (!isSearching) {
                    val gridLayoutManager = recyclerView
                        .layoutManager as GridLayoutManager?
                    val currentItems = gridLayoutManager?.childCount
                    val totalItems = gridLayoutManager?.itemCount
                    val scrollOutItems = gridLayoutManager?.findFirstVisibleItemPosition()
                    val expectedTotal = scrollOutItems?.let { currentItems?.plus(it) }
                    if (isScrolling && expectedTotal == totalItems && !isSearching) {
                        isScrolling = false
                        page = page.plus(1)
                        presenter.loadMovies(page)
                    }
                }
            }
        })
    }
}