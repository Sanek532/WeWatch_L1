package com.example.wewatch_l1.add

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.example.wewatch_l1.R
import com.example.wewatch_l1.model.Movie
import com.example.wewatch_l1.model.MyDatabase
import com.example.wewatch_l1.search.SearchActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    lateinit var titleEditText: EditText
    lateinit var releaseDateEditText: EditText
    lateinit var movieImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        val dataBase = MyDatabase.getDb(this)
        titleEditText = findViewById(R.id.movie_title)
        val searchBtn = findViewById<ImageButton>(R.id.search_button)
        val addBtn = findViewById<Button>(R.id.add_button)
        releaseDateEditText = findViewById(R.id.movie_release_date)
        movieImageView = findViewById(R.id.movie_imageview)
        searchBtn.setOnClickListener {
            if (titleEditText.text.isEmpty()) {
                Toast.makeText(
                    this,
                    "Название не может быть пустым",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            else {
                val title = titleEditText.text.toString()
                val intent = Intent(this@AddActivity, SearchActivity::class.java)
                intent.putExtra(SearchActivity.SEARCH_QUERY, title)
                startActivityForResult(intent, SEARCH_ACTIVITY_REQUEST_CODE)
            }
        }

        addBtn.setOnClickListener {
            if (titleEditText.text.isEmpty() || releaseDateEditText.text.isEmpty()) {
                Toast.makeText(
                    this,
                    "Поля 'Заголовок' и 'Год релиза' не может быть пустыми",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            else {
                if (movieImageView.tag == null) movieImageView.tag = ""
                CoroutineScope(Dispatchers.IO).launch {
                    val movie = Movie(null, titleEditText.text.toString(), releaseDateEditText.text.toString(), movieImageView.tag.toString())
                    dataBase.getDao().insert(movie)
                }
                setResult(Activity.RESULT_OK)
                finish()
            }

        }
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
        const val SEARCH_ACTIVITY_REQUEST_CODE = 2
    }
}