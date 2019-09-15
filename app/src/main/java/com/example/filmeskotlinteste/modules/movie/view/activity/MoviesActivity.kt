package com.example.filmeskotlinteste.modules.movie.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.filmeskotlinteste.R
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
        setContentView(R.layout.activity_filmes)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        setupRecyclerView()
        subscribeUI()

        configSwipeRefresh()
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.requestFilmes(page)
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
                        movieViewModel.requestFilmes(page++)
                    }
                }
            })
        }
    }

    private fun configSwipeRefresh() {
        swiperefreshMovies.setOnRefreshListener {
            movieViewModel.requestFilmes(1)
        }
    }

    private fun subscribeUI() {
        with(movieViewModel) {

            onLoadStarted.observe(this@MoviesActivity, Observer {
                progressBarMovies.visibility = VISIBLE
            })

            onLoadMoreStarted.observe(this@MoviesActivity, Observer {
                progressBarLoadMore.visibility = VISIBLE
            })

            onLoadFinished.observe(this@MoviesActivity, Observer {
                progressBarMovies.visibility = GONE
                progressBarLoadMore.visibility = GONE
                swiperefreshMovies.isRefreshing = false
            })

            onError.observe(this@MoviesActivity, Observer { msg ->
                Log.d("MOVIES-ERROR", msg)
            })

            filmes.observe(this@MoviesActivity, Observer { filmes ->
                filmesAdapter.refresh(filmes)
            })
        }
    }
}


