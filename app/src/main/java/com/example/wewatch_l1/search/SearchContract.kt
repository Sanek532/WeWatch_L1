package com.example.wewatch_l1.search

import com.example.wewatch_l1.model.SearchResponse

class SearchContract {
    interface PresenterInterface {
        fun getSearchResults(query: String)
        fun stop()
    }
    interface ViewInterface {
        fun displayResult(searchResponse: SearchResponse)
        fun displayMessage(message: String)
        fun displayError(message: String)
    }

}