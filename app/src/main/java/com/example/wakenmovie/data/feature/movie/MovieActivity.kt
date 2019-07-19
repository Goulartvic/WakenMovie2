package com.example.wakenmovie.data.feature.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tanheta.wakenmovie.BuildConfig
import com.tanheta.wakenmovie.R
import com.example.wakenmovie.base.extensions.extra
import com.example.wakenmovie.base.extensions.isVisible
import com.example.wakenmovie.base.extensions.loadImage
import com.example.wakenmovie.base.extensions.showToast
import com.example.wakenmovie.data.model.dto.MovieDto
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MovieActivity : AppCompatActivity(), MovieContract.View {

    override val presenter by inject<MovieContract.Presenter> {parametersOf(this)}

    private val movieId by extra(EXTRA_MOVIE_ID, 0L)

    companion object {
        private const val EXTRA_MOVIE_ID = "movieId"

        fun startMovieActivity(context: Context, movieId: Long) {
            val intent = Intent(context, MovieActivity::class.java)

            intent.putExtra(EXTRA_MOVIE_ID, movieId)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie)
        setupViews()
        presenter.loadMovie(movieId)
    }

    private fun setupViews() {
        setSupportActionBar(movieToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        movieToolbar.tmTxtTitle.setText(R.string.app_name)
        movieToolbar.tmImgSearch.visibility = View.GONE
        movieOverviewReadMore.setOnClickListener{
            movieOverview.maxLines = 100
            movieOverviewReadMore.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showLoadingMovies() {
        movieViewLoading.isVisible = true
    }

    override fun hideLoadingMovies() {
        movieViewLoading.isVisible = false
    }

    override fun onSuccessfulLoadMovie(movie: MovieDto) {
        detailImgCover.loadImage(BuildConfig.IMAGE_URL + movie.posterPath)
        detailGameTitle.text = movie.title
        movieRate.text = baseContext.getString(R.string.item_rating, movie.voteAverage.toString())
        originalLanguage.text = baseContext.getString(R.string.item_original_language, movie.originalLanguage.toUpperCase())
        releaseDate.text = baseContext.getString(R.string.item_release_date, movie.releaseDate)
        movieOverview.text = movie.overview
        movieOverviewReadMore.visibility = View.VISIBLE
    }

    override fun onFailuereLoadMovie(message: String) {
        showToast(message)
    }
}