package com.example.wewatch_l1.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SearchResponse {
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null

    /*
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    */
    @SerializedName("Response")
    @Expose
    var response: Boolean? = false

    @SerializedName("Search")
    @Expose
    var results: List<Movie>? = null
}