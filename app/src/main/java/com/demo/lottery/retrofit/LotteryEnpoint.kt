package com.demo.lottery.retrofit

import com.demo.lottery.models.Lottery
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface LotteryEnpoint {

    @GET("common.do")
    fun getLotoResult(
        @Query("method") method: String = "getLottoNumber",
        @Query("drwNo") no: Int
    ): Observable<Lottery>
}