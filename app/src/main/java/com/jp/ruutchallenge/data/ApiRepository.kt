package com.jp.ruutchallenge.data

import android.util.Log
import com.jp.ruutchallenge.model.AlphaVantageResponse
import com.jp.ruutchallenge.service.RetrofitInstance
import javax.inject.Inject

class ApiRepository @Inject constructor() {

    private val TAG = "API_REPOSITORY"
    private val apiService = RetrofitInstance.apiService

    suspend fun getData(): AlphaVantageResponse {
        // TODO: Request parameters for dynamic data
        val function = "TIME_SERIES_INTRADAY"
        val symbol = "IBM"
        val interval = "5min"
        val apiKey = "DR68D22NJOVGTD8S"
        val response = apiService.query(function, symbol, interval, apiKey)
        return if (response.isSuccessful) {
            response.body() ?: AlphaVantageResponse.EMPTY
        } else {
            Log.e(TAG, response.errorBody().toString())
            AlphaVantageResponse.EMPTY
        }
    }

}