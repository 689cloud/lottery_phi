package com.demo.lottery.activities

import com.demo.lottery.models.mvi.DeepLinkViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface DeeplinkView : MvpView {
    fun getLotteryResult(): Observable<Int>
    fun renderData(state: DeepLinkViewState)
}