package com.demo.lottery.activities

import com.demo.lottery.models.Lottery
import com.demo.lottery.models.mvi.MainViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface MainView: MvpView {
    fun getLotteryResult(): Observable<Int>
    fun generateLottery(): Observable<Unit>
    fun calculateResult(): Observable<Pair<Lottery? , Lottery?>>
    fun renderData(state: MainViewState)
}