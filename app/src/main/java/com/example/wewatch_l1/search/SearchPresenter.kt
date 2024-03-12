package com.example.wewatch_l1.search

import com.example.wewatch_l1.model.RemoteDataSource

class SearchPresenter(
    private var viewInterface: SearchContract.ViewInterface,
    private var dataSource: RemoteDataSource
) : SearchContract.PresenterInterface {
    private val TAG = "SearchPresenter"
}