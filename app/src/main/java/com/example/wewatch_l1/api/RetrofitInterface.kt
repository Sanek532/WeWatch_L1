package com.example.wewatch_l1.api

import com.example.wewatch_l1.model.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "ClientAPI"
interface RetrofitInterface {
    @GET("/")
    fun searchMovie(@Query("apikey") api_key: String, @Query("s") s: String): Observable<SearchResponse>

}