package com.demo.lottery.activities

import com.demo.lottery.models.Lottery
import com.demo.lottery.models.mvi.TrendingViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface TrendingView: MvpView {
    fun calculateTrending(): Observable<List<Lottery>>
    fun renderTrending(state: TrendingViewState)
}