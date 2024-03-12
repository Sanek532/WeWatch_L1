package com.example.wewatch_l1.model

import android.util.Log
import com.example.wewatch_l1.api.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class RemoteDataSource {
    private val TAG = "RemoteDataSource"

    fun searchResultsObservable(query: String): Observable<SearchResponse> {
        Log.d(TAG, "search/movie")
        return RetrofitClient.moviesApi
            .searchMovie(RetrofitClient.API_KEY, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}