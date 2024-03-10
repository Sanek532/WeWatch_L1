package com.sample.watch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wewatch_l1.R
import com.example.wewatch_l1.model.Movie
import com.squareup.picasso.Picasso
import java.util.HashSet

class MainAdapter(var list: List<Movie>, var context: Context): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
        val viewHolder = MainHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.titleTextView.text = list[position].title
        holder.releaseDateTextView.text = list[position].year
        if (list[position].posterUrl.equals("")) {
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_local_movies_gray))
        } else {
            Picasso.get().load(list[position].posterUrl).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.title_textview)
        var releaseDateTextView: TextView = view.findViewById(R.id.release_date_textview)
        var imageView: ImageView = view.findViewById(R.id.movie_imageview)
        var checkBox: CheckBox = view.findViewById(R.id.checkbox)

        init {
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMovies.contains(list[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMovies.add(list[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMovies.add(list[adapterPosition])
                }
            }
        }
    }


}