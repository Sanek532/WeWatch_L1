package com.example.wewatch_l1.main

import android.support.annotation.NonNull
import android.util.Log
import com.example.wewatch_l1.model.LocalDataSource
import com.example.wewatch_l1.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable

class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var dataSource: LocalDataSource): MainContract.PresenterInterface {
    private val TAG = "MainPresenter"

    private val compositeDisposable = CompositeDisposable()
    //1
    val myMoviesObservable: io.reactivex.Observable<List<Movie>>
        get() = dataSource.allMovies
    //2
    val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {
            override fun onNext(movieList: List<Movie>) {
                if (movieList == null || movieList.size == 0) {
                    viewInterface.displayNoMovies()
                } else {
                    viewInterface.displayMovies(movieList)
                }
            }
            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error fetching movie list.", e)
                viewInterface.displayError("Error fetching movie list.")
            }
            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }
    //3
    override fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(myMoviesDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun onDeleteTapped(selectedMovies: HashSet<*>) {
        for (movie in selectedMovies) {
            dataSource.delete(movie as Movie)
        }
        if (selectedMovies.size == 1) {
            viewInterface.displayMessage("Movie deleted")
        } else if (selectedMovies.size > 1) {
            viewInterface.displayMessage("Movies deleted")
        }
    }
}