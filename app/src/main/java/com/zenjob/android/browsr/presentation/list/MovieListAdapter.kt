package com.zenjob.android.browsr.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zenjob.android.browsr.model.Movie
import com.zenjob.android.browsr.databinding.ViewholderMovieItemBinding
import com.zenjob.android.browsr.util.DateUtil
import com.zenjob.android.browsr.util.DateUtil.Companion.toString
import java.util.*
import javax.inject.Inject


class MovieListAdapter @Inject constructor() :
    PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffCallback()) {

    var listener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewholderMovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.listener = itemClickListener
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.binding.title.text = movie?.title
        (DateUtil.getCurrentDateTime().toString(
            DateUtil.dd_MMM_YY,
            Locale.getDefault()
        )).also { holder.binding.releaseDate.text = it }

        holder.binding.rating.text = movie?.voteAverage.toString()
        Glide.with(holder.itemView.context)
            .load("https://www.themoviedb.org/t/p/w440_and_h660_face" + movie?.image)
            .into(holder.binding.roundedImageView)
        holder.binding.root.setOnClickListener {
            // Triggers click upwards to the adapter on click
            movie?.let { it1 -> listener?.onMovieClicked(holder.binding, it1) }
        }

    }

    interface ItemClickListener {
        fun onMovieClicked(binding: ViewholderMovieItemBinding, movie: Movie)
    }

    class MovieViewHolder(val binding: ViewholderMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
