package com.jp.ruutchallenge.service

import com.jp.ruutchallenge.model.AlphaVantageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/query")
    suspend fun query(
        @Query("function") function: String,
        @Query("symbol") symbol: String,
        @Query("interval") interval: String,
        @Query("apikey") apikey: String,
    ): Response<AlphaVantageResponse>

}