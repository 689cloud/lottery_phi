package com.demo.lottery.presenters

import android.util.Log
import com.demo.lottery.activities.MainView
import com.demo.lottery.helpers.LotteryHelper
import com.demo.lottery.models.mvi.MainActionState
import com.demo.lottery.models.mvi.MainViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter: MviBasePresenter<MainView, MainViewState>() {
    override fun bindIntents() {
        val getLotteryResult = intent(MainView::getLotteryResult)
            .subscribeOn(Schedulers.io())
            .doOnNext { Log.d("Action", "getLotteryResult") }
            .switchMap { LotteryHelper.getLotteryResult(it) }
            .observeOn(AndroidSchedulers.mainThread())

        val generateLottery = intent(MainView::generateLottery)
            .doOnNext { Log.d("Action", "generateLottery") }
            .switchMap { LotteryHelper.generateLottery()}
            .observeOn(AndroidSchedulers.mainThread())

        val calculatePrize = intent(MainView::calculateResult)
            .doOnNext { Log.d("Action", "caculatePrize") }
            .switchMap{
                LotteryHelper.calculatePrize(it.first, it.second)
            }
            .observeOn(AndroidSchedulers.mainThread())
        val initState = MainViewState()
        val allViewState = Observable.merge(
            getLotteryResult, generateLottery, calculatePrize).scan(initState, this::viewStateReducer)
        subscribeViewState(allViewState, MainView::renderData)
    }

    private fun viewStateReducer(previousState: MainViewState, currentState: MainActionState): MainViewState {
        return when (currentState) {
            MainActionState.LoadingState -> {
                previousState.copy()
                    .isLoadingLotteryResult(true)
                    .prize(null)
                    .build()
            }
            is MainActionState.LotteryResultState -> {
                previousState.copy()
                    .isLoadingLotteryResult(false)
                    .lotteryResult(currentState.lottery)
                    .build()
            }
            is MainActionState.LotteryRandomState -> {
                previousState.copy()
                    .lotteryGenerated(currentState.lottery)
                    .build()
            }
            is MainActionState.LotteryPrizeState -> {
                previousState.copy()
                    .prize(currentState.prize)
                    .build()
            }
            is MainActionState.ErrorState -> {
                previousState.copy()
                    .error(currentState.error)
                    .build()
            }
        }
    }

}