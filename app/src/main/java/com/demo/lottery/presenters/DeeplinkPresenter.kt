package com.demo.lottery.presenters

import com.demo.lottery.activities.DeeplinkView
import com.demo.lottery.helpers.LotteryHelper
import com.demo.lottery.models.mvi.DeepLinkViewState
import com.demo.lottery.models.mvi.DeeplinkActionState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DeeplinkPresenter: MviBasePresenter<DeeplinkView, DeepLinkViewState>() {
    override fun bindIntents() {
        val lotteryResult = intent(DeeplinkView::getLotteryResult)
            .subscribeOn(Schedulers.io())
            .switchMap { LotteryHelper.getLotteryResultDeeplink(it) }
            .observeOn(AndroidSchedulers.mainThread())

        val initState = DeepLinkViewState()
        val stateObservable = lotteryResult.scan(initState, this::viewStateReducer)

        subscribeViewState(stateObservable, DeeplinkView::renderData)
    }

    private fun viewStateReducer(previousState: DeepLinkViewState, currentState: DeeplinkActionState): DeepLinkViewState {
        return when(currentState) {
            DeeplinkActionState.LoadingState -> {
                previousState.copy()
                    .loading(true)
                    .error(null)
                    .lotteryResult(null)
                    .build()
            }
            is DeeplinkActionState.LotteryResultState -> {
                previousState.copy()
                    .loading(false)
                    .lotteryResult(currentState.lottery)
                    .build()
            }

            is DeeplinkActionState.ErrorState -> {
                previousState.copy()
                    .loading(false)
                    .error(currentState.error)
                    .build()
            }
        }
    }
}