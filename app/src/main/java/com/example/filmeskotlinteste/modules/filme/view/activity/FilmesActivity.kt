package com.example.filmeskotlinteste.modules.filme.view.activity

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.filmeskotlinteste.R
import com.example.filmeskotlinteste.modules.filme.adapter.FilmesAdapter
import com.example.filmeskotlinteste.modules.filme.viewmodel.FilmeViewModel
import kotlinx.android.synthetic.main.activity_filmes.*
import org.jetbrains.anko.startActivity

class FilmesActivity : AppCompatActivity() {

    val ID_CONSULTA = "ID_CONSULTA"

    private lateinit var consultaViewModel: FilmeViewModel

    private val filmesAdapter by lazy {
        FilmesAdapter(clickListener = { idFilme ->
            Toast.makeText(this, idFilme.toString(), LENGTH_SHORT).show()
//            startActivity<DetalhesFilmeActivity>(
//                ID_CONSULTA to idFilme
//            )
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filmes)

        consultaViewModel = ViewModelProviders.of(this).get(FilmeViewModel::class.java)

        setupRecyclerView()
        subscribeUI()
    }

    override fun onResume() {
        super.onResume()
        consultaViewModel.requestFilmes()
    }

    private fun setupRecyclerView() {
        with(recyclerviewMeusFilmes) {
            adapter = filmesAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
        }
    }

    private fun subscribeUI() {
        with(consultaViewModel) {
            filmes.observe(this@FilmesActivity, Observer { filmes ->
                filmesAdapter.refresh(filmes)
            })
        }
    }
}
