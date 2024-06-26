package com.example.wewatch_l1.add

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.example.wewatch_l1.R
import com.example.wewatch_l1.model.LocalDataSource
import com.example.wewatch_l1.model.Movie
import com.example.wewatch_l1.model.MyDatabase
import com.example.wewatch_l1.search.SearchActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity(), AddContract.ViewInterface {
    lateinit var titleEditText: EditText
    lateinit var releaseDateEditText: EditText
    lateinit var movieImageView: ImageView

    private lateinit var addMoviePresenter: AddPresenter
    fun setupPresenter() {
        val dataSource = LocalDataSource(application)
        addMoviePresenter = AddPresenter(this, dataSource)
    }

    fun setupViews() {
        titleEditText = findViewById(R.id.movie_title)
        releaseDateEditText = findViewById(R.id.movie_release_date)
        movieImageView = findViewById(R.id.movie_imageview)
    }

    override fun returnToMain() {
        setResult(Activity.RESULT_OK)
        finish()
    }


    fun onClickAddMovie (view: View) {
        val title = titleEditText.text.toString()
        val releaseDate = releaseDateEditText.text.toString()
        val posterPath = if (movieImageView.tag != null )
            movieImageView.tag.toString() else ""
        addMoviePresenter.addMovie (title, releaseDate, posterPath)
    }

    fun goToSearchMovieActivity(v: View) {
        val title = titleEditText.text.toString()
        val intent = Intent(this@AddActivity, SearchActivity::class.java)
        intent.putExtra(SearchActivity.SEARCH_QUERY, title)
        startActivityForResult(intent, SEARCH_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        setupPresenter()
        setupViews()
    }

    override fun displayMessage (message: String ) {
        Toast.makeText( this@AddActivity , message,
            Toast.LENGTH_LONG).show()
    }
    override fun displayError (message: String ) {
        displayMessage (message)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        this@AddActivity.runOnUiThread {
            titleEditText.setText(data?.getStringExtra(SearchActivity.EXTRA_TITLE))
            releaseDateEditText.setText(data?.getStringExtra(SearchActivity.EXTRA_RELEASE_DATE))
            movieImageView.tag = data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH) ?: ""
            if (movieImageView.tag !== "") Picasso.get().load(data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)).into(movieImageView)
            Log.d("AddActivity", movieImageView.tag.toString())

        }
    }

    companion object {
        const val SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2
    }
}