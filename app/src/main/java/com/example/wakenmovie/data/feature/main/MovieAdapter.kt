package com.example.wakenmovie.data.feature.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tanheta.wakenmovie.BuildConfig
import com.tanheta.wakenmovie.R
import com.example.wakenmovie.base.extensions.loadImage
import com.example.wakenmovie.data.model.dto.SimpleMovieDto
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.Holder>() {

    var data: List<SimpleMovieDto> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemListener: (item: SimpleMovieDto) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(data[position])
    }

    fun setOnItemClickListener(listener:(item: SimpleMovieDto) -> Unit) {
        onItemListener = listener
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemListener(data[adapterPosition])
            }
        }

        fun render(item: SimpleMovieDto) {
            itemView.imImgCover.loadImage(BuildConfig.IMAGE_URL + item.posterPath)
            itemView.imMovieTitle.text = item.title
            itemView.imRate.text = itemView.context.getString(R.string.item_rating, item.voteAverage.toString())
        }
    }
}