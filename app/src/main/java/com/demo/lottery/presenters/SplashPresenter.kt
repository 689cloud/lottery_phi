package com.demo.lottery.presenters


import android.util.Log
import com.demo.lottery.activities.SplashView
import com.demo.lottery.helpers.LotteryHelper
import com.demo.lottery.helpers.PrefsHelper
import com.demo.lottery.helpers.Session
import com.demo.lottery.models.mvi.SplashActionState
import com.demo.lottery.models.mvi.SplashViewState
import com.demo.lottery.retrofit.LotteryApi
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.get

class SplashPresenter: MviBasePresenter<SplashView, SplashViewState>(), KoinComponent {

    override fun bindIntents() {
        val loadData = intent(SplashView::emitLoadData)
            .subscribeOn(Schedulers.io())
            .doOnNext { Log.d("Action", "load event.") }
            .switchMap { LotteryHelper.getLotteryDatas() }
            .observeOn(AndroidSchedulers.mainThread())

        val firstloadData = intent(SplashView::emitFirtstLoadData)
            .subscribeOn(Schedulers.io())
            .doOnNext { Log.d("Action", "First time page load event.") }
            .switchMap { LotteryHelper.getLotteryDatas() }
            .observeOn(AndroidSchedulers.mainThread())

        val allViewState: Observable<SplashActionState> = Observable.merge(
            loadData, firstloadData)

        val prefsHelper:PrefsHelper = get()
        val initializeState = SplashViewState(true, prefsHelper.isFirstTime())

        val stateObservable = allViewState
            .scan(initializeState, this::viewStateReducer)

        subscribeViewState(stateObservable, SplashView::renderData)
    }

    private fun viewStateReducer(previousState: SplashViewState, currentState: SplashActionState): SplashViewState {
        return when(currentState) {
            SplashActionState.LoadingState -> {
                previousState.copy()
                    .isLoading(true)
                    .error(null)
                    .data(null)
                    .build()
            }
            is SplashActionState.DataState -> {
                previousState.copy()
                    .data(currentState.data)
                    .build()
            }
            is SplashActionState.ErrorState -> {
                previousState.copy()
                    .isLoading(false)
                    .error(currentState.error)
                    .data(null)
                    .build()
            }
        }
    }
}

