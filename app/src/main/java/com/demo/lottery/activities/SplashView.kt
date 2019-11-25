package com.demo.lottery.activities

import com.demo.lottery.models.mvi.SplashViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface SplashView: MvpView {
    fun emitLoadData(): Observable<Unit>
    fun renderData(state: SplashViewState)
}