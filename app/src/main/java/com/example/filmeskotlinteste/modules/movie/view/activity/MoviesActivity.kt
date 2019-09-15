package com.example.filmeskotlinteste.modules.movie.view.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.filmeskotlinteste.modules.movie.adapter.MoviesAdapter
import com.example.filmeskotlinteste.modules.movie.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_filmes.*
import org.jetbrains.anko.startActivity


class MoviesActivity : AppCompatActivity() {

    companion object {
        const val ID_MOVIE = "ID_MOVIE"
    }

    private var page = 1

    private lateinit var movieViewModel: MovieViewModel
    //    private lateinit var searchMovieViewModel: SearchMovieViewModel
    private lateinit var searchView: SearchView

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val filmesAdapter by lazy {
        MoviesAdapter(clickListener = { idFilme ->
            startActivity<MovieDetailsActivity>(
                ID_MOVIE to idFilme
            )
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.filmeskotlinteste.R.layout.activity_filmes)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        setupRecyclerView()
        subscribeUI()

        configSwipeRefresh()
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.requestMovies(page)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(com.example.filmeskotlinteste.R.menu.menu, menu)

        val searchItem = menu.findItem(com.example.filmeskotlinteste.R.id.action_search)
        searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(onQueryTextListener)

        return true
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return true
        }

        override fun onQueryTextChange(query: String): Boolean {
            if (query.trim() == "") {
                movieViewModel.isSearch = false
                movieViewModel.requestMovies(1)
            } else {
                movieViewModel.isSearch = true
                movieViewModel.searchMovies(query)
            }
            return true
        }
    }

    private fun setupRecyclerView() {

        linearLayoutManager = LinearLayoutManager(this, VERTICAL, false)

        with(recyclerviewMeusFilmes) {
            adapter = filmesAdapter
            setHasFixedSize(true)
            layoutManager = linearLayoutManager

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()

                    if (totalItemCount == lastVisibleItemPosition + 1) {
                        movieViewModel.requestMovies(page++)
                    }
                }
            })
        }
    }

    private fun configSwipeRefresh() {
        swiperefreshMovies.setOnRefreshListener {
            movieViewModel.requestMovies(1)
        }
    }

    private fun subscribeUI() {
        with(movieViewModel) {

            onLoadMoreStarted.observe(this@MoviesActivity, Observer {
                progressBarLoadMore.visibility = VISIBLE
            })

            onLoadFinished.observe(this@MoviesActivity, Observer {
                progressBarMovies.visibility = GONE
                progressBarLoadMore.visibility = GONE
                swiperefreshMovies.isRefreshing = false
            })

            onError.observe(this@MoviesActivity, Observer { errorMessage ->
                Log.d("MOVIES-ERROR", errorMessage)
            })

            movies.observe(this@MoviesActivity, Observer { movies ->
                filmesAdapter.refresh(movies)
            })
        }

//        with(searchMovieViewModel) {
//
//            movies.observe(this@MoviesActivity, Observer {movies ->
//                filmesAdapter.refresh(movies)
//            })
//        }
    }
}


