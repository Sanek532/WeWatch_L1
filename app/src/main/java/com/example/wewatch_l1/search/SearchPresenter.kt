package com.example.wewatch_l1.search

import android.support.annotation.NonNull
import android.util.Log
import com.example.wewatch_l1.model.RemoteDataSource
import com.example.wewatch_l1.model.SearchResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter(
    private var viewInterface: SearchContract.ViewInterface,
    private var dataSource: RemoteDataSource
) : SearchContract.PresenterInterface {
    private val TAG = "SearchPresenter"

    private val compositeDisposable = CompositeDisposable()

    //1
    val searchResultsObservable: (String) -> Observable<SearchResponse> =
        { query -> dataSource.searchResultsObservable(query) }

    //2
    val observer: DisposableObserver<SearchResponse>
        get() = object : DisposableObserver<SearchResponse>() {
            override fun onNext(@NonNull searchResponse: SearchResponse) {
                Log.d(TAG, "OnNext" + searchResponse.totalResults)
                viewInterface.displayResult(searchResponse)
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error fetching movie data.", e)
                viewInterface.displayError("Error fetching movie data.")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    //3
    override fun getSearchResults(query: String) {
        val searchResultsDisposable = searchResultsObservable(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(searchResultsDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}