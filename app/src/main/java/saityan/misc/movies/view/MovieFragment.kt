package saityan.misc.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import saityan.misc.movies.databinding.FragmentMovieBinding
import saityan.misc.movies.repository.Movie

class MovieFragment (
    private val movie: Movie
) : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding: FragmentMovieBinding
        get() {
            return _binding!!
        }

    companion object {
        fun newInstance(movie: Movie) = MovieFragment(movie)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = "https://image.tmdb.org/t/p/original" + movie.poster_path
        with (binding) {
            movieTitle.text = movie.title
            movieOverview.text = movie.overview
            movieReleaseDate.text = "Release Date: " + movie.release_date
            movieVoteAverage.text = "Average Vote: " + movie.vote_average
            Picasso
                .with(requireContext())
                .load(url)
                .into(binding.movieImage)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
