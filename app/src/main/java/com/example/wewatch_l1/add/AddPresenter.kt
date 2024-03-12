package com.example.wewatch_l1.add

import com.example.wewatch_l1.model.LocalDataSource
import com.example.wewatch_l1.model.Movie

class AddPresenter(
    private var viewInterface: AddContract.ViewInterface,
    private var dataSource: LocalDataSource
) :
    AddContract.PresenterInterface {
    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        //1
        if (title.isEmpty()) {
            viewInterface.displayError ("Название фильма не может быть пустым")
        } else {
            //2
            val movie = Movie(null, title, releaseDate, posterPath)
            dataSource.insert(movie)
            viewInterface.returnToMain()
        }
    }
}