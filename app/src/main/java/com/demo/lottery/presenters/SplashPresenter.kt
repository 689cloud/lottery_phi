package com.demo.lottery.presenters


import android.util.Log
import com.demo.lottery.activities.SplashView
import com.demo.lottery.models.mvi.SplashActionState
import com.demo.lottery.models.mvi.SplashViewState
import com.demo.lottery.retrofit.LotteryApi
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SplashPresenter: MviBasePresenter<SplashView, SplashViewState>() {
    override fun bindIntents() {
        val loadData = intent(SplashView::emitLoadData)
            .subscribeOn(Schedulers.io())
            .debounce(200, TimeUnit.MILLISECONDS)
            .doOnNext { Log.d("Action", "First time page load event.") }
            .flatMap { getLotteryDatas() }
            .observeOn(AndroidSchedulers.mainThread())

        val initializeState = SplashViewState(true)
        val stateObservable = loadData
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

    private fun getLotteryDatas(): Observable<SplashActionState> {
        return Observable.fromIterable(List(1) {it + 1})
            .flatMap {
                LotteryApi.getClient()
                    .getLotoResult(no = it)
                    .subscribeOn(Schedulers.io())
            }
            .toSortedList { lottery, lottery2 -> if(lottery.drwNo < lottery2.drwNo) -1 else 1 }
            .toObservable()
            .map<SplashActionState>{SplashActionState.DataState(it)}
            .startWith(SplashActionState.LoadingState)
            .onErrorReturn { SplashActionState.ErrorState(it) }

    }

}

