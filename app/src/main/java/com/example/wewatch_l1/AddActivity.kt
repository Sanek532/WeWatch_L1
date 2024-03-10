package com.example.wewatch_l1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
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
                }
                setResult(Activity.RESULT_OK)
                finish()
            }

        }
    }
}