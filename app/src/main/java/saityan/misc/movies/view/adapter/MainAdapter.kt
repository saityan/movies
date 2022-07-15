package saityan.misc.movies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import saityan.misc.movies.databinding.MainFragmentItemBinding
import saityan.misc.movies.repository.Movie
import saityan.misc.movies.view.MainFragment

class MainAdapter (
    private val fragment: MainFragment,
    private var data: List<Movie>
) : RecyclerView.Adapter<MainAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.MovieViewHolder =
        MovieViewHolder(
            MainFragmentItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MainAdapter.MovieViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<Movie>) {
        this.data = data
    }

    inner class MovieViewHolder (private val binding: MainFragmentItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            fragment.showMovieInfo(adapterPosition)
        }

        fun bind (position: Int) = with(binding) {
            mainTitle.text = data[position].title
        }
    }
}
