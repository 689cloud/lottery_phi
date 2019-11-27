package com.demo.lottery.presenters

import com.demo.lottery.activities.TrendingView
import com.demo.lottery.helpers.LotteryHelper
import com.demo.lottery.models.mvi.TrendingActionState
import com.demo.lottery.models.mvi.TrendingViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers

class TrendingPresenter(): MviBasePresenter<TrendingView, TrendingViewState>() {
    override fun bindIntents() {
        val calculateTrending = intent(TrendingView::calculateTrending)
            .switchMap { LotteryHelper.calculateTrending(it) }
            .observeOn(AndroidSchedulers.mainThread())

        val initializeState = TrendingViewState()
        val stateObservable = calculateTrending
            .scan(initializeState, this::viewStateReducer)
        subscribeViewState(stateObservable, TrendingView::renderTrending)
    }

    private fun viewStateReducer(previousState: TrendingViewState, currentState: TrendingActionState): TrendingViewState {
        return when(currentState) {
            is TrendingActionState.CalculateTrending -> TrendingViewState(currentState.data)
        }
    }
}