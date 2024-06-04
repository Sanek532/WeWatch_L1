package com.example.wewatch_l1.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wewatch_l1.R
import com.example.wewatch_l1.api.RetrofitInterface
import com.example.wewatch_l1.model.Item
import com.example.wewatch_l1.model.RemoteDataSource
import com.example.wewatch_l1.model.SearchResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TAG = "SearchActivity"

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    //private lateinit var noMoviesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private var query = ""
    lateinit var mytext: EditText

    private lateinit var searchPresenter: SearchPresenter
    private fun setupPresenter() {
        val dataSource = RemoteDataSource()
        searchPresenter = SearchPresenter(this, dataSource)
    }

    private fun setupViews() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progress_bar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mytext = findViewById(R.id.mytext)
    }

    override fun onStart() {
        super.onStart()
        progressBar.visibility = View.VISIBLE
        searchPresenter.getSearchResults(query)
    }

    override fun onStop() {
        super.onStop()
        searchPresenter.stop()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupPresenter()
        setupViews()

        val i = intent
        query = i.getStringExtra(SEARCH_QUERY) ?: ""
    }

    override fun displayResult(searchResponse: SearchResponse) {
        progressBar.visibility = View.INVISIBLE

        if (searchResponse.totalResults == null || searchResponse.totalResults == 0) {
            recyclerView.visibility = View.INVISIBLE
           // noMoviesTextView.visibility = View.VISIBLE
        } else {
            adapter = SearchAdapter(searchResponse.results
                ?: arrayListOf(), itemListener,this@SearchActivity)
            recyclerView.adapter = adapter

            recyclerView.visibility = View.VISIBLE
                //noMoviesTextView.visibility = View.INVISIBLE
        }
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@SearchActivity, message, Toast.LENGTH_LONG).show()
    }

    fun showToast(string: String) {
        Toast.makeText(this@SearchActivity, string, Toast.LENGTH_LONG).show()
    }

    override fun displayError(string: String) {
        showToast(string)
    }


    internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(view: View, position: Int) {
            val movie = adapter.getItemAtPosition(position)

            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_TITLE, movie.title)
            replyIntent.putExtra(EXTRA_RELEASE_DATE, movie.getReleaseYearFromDate().toString())
            replyIntent.putExtra(EXTRA_POSTER_PATH, movie.posterPath)
            setResult(RESULT_OK, replyIntent)

            finish()

        }
    }

    companion object {
        val SEARCH_QUERY = "searchQuery"
        val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }




    interface RecyclerItemListener {
        fun onItemClick(v: View, position: Int)
    }
}