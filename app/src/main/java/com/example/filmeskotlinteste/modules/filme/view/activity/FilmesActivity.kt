package com.example.filmeskotlinteste.modules.filme.view.activity

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.filmeskotlinteste.R
import com.example.filmeskotlinteste.modules.filme.adapter.FilmesAdapter
import com.example.filmeskotlinteste.modules.filme.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_filmes.*

class FilmesActivity : AppCompatActivity() {

    val ID_MOVIE = "ID_MOVIE"

    private var page = 1

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val filmesAdapter by lazy {
        FilmesAdapter(clickListener = { idFilme ->
            Toast.makeText(this, idFilme.toString(), LENGTH_SHORT).show()
//            startActivity<DetalhesFilmeActivity>(
//                ID_MOVIE to idFilme
//            )
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filmes)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        setupRecyclerView()
        subscribeUI()
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

    private fun subscribeUI() {
        with(movieViewModel) {
            filmes.observe(this@FilmesActivity, Observer { filmes ->
                filmesAdapter.refresh(filmes)
            })
        }
    }
}


