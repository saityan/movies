package saityan.misc.movies.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import saityan.misc.movies.R
import saityan.misc.movies.databinding.FragmentMainBinding
import saityan.misc.movies.repository.Movie
import saityan.misc.movies.view.adapter.MainAdapter
import saityan.misc.movies.view.viewmodel.MainViewModel
import saityan.misc.movies.view.viewmodel.MoviesData

class MainFragment : Fragment(), ShowMovieInfo {

    private var moviesData: List<Movie> = mutableListOf()

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        this.adapter = MainAdapter(this, moviesData)
        this.recyclerView = binding.mainRecycler
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = this.adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMoviesLiveData().observe(viewLifecycleOwner, { renderMoviesData(it) })
        viewModel.sendServerRequest()
    }

    private fun renderMoviesData(data: MoviesData) {
        when(data) {
            is MoviesData.Success -> {
                this.moviesData = data.serverResponseData.results
                adapter.setData(moviesData)
                this.adapter.notifyDataSetChanged()
            }
            is MoviesData.Error -> {
                toast(data.error.message)
            }
            is MoviesData.Loading -> {}
        }
    }

    private fun Fragment.toast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    override fun showMovieInfo(position: Int) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MovieFragment.newInstance(moviesData[position]))
            .addToBackStack("")
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
