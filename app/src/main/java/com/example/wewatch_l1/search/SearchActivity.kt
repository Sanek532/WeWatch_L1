package com.example.wewatch_l1.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wewatch_l1.R
import com.example.wewatch_l1.api.RetrofitInterface
import com.example.wewatch_l1.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TAG = "SearchActivity"

class SearchActivity : AppCompatActivity() {
    private lateinit var list: List<Item>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var noMoviesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private var query = ""
    lateinit var mytext: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progress_bar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mytext = findViewById(R.id.mytext)

        val i = intent
        query = i.getStringExtra(SEARCH_QUERY) ?: ""

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val clientApi: RetrofitInterface = retrofit.create<RetrofitInterface>(RetrofitInterface::class.java)

//        CoroutineScope(Dispatchers.IO).launch {
//            val response = clientApi.fetchResponse("53243e84",query)
//            runOnUiThread {
//                list = response.items?: emptyList()
//                adapter = SearchAdapter(list, itemListener, this@SearchActivity)
//                recyclerView.adapter = adapter
//                progressBar.visibility = View.INVISIBLE
//            }
//            Log.d(TAG, "Response received: $response")
//        }
    }


    internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(view: View, position: Int) {
            val movie = adapter.getItemAtPosition(position)
            Log.d(TAG, movie.title)
            Log.d(TAG, movie.getReleaseYearFromDate().toString())
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