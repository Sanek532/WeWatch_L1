package com.example.wewatch_l1.api

import com.example.wewatch_l1.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "ClientAPI"
interface ClientApi {
    @GET("/")
    suspend fun fetchResponse(@Query("apikey") api_key: String, @Query("s") s: String): SearchResponse

}