package com.demo.lottery.activities

import android.os.Bundle
import android.util.Log
import com.demo.lottery.R
import com.demo.lottery.models.mvi.SplashViewState
import com.demo.lottery.presenters.SplashPresenter
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable

class SplashActivity : MviActivity<SplashView, SplashPresenter>() , SplashView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun createPresenter(): SplashPresenter {
        return SplashPresenter()
    }

    override fun emitLoadData(): Observable<Unit> {
        return Observable.just(Unit)
    }

    override fun renderData(state: SplashViewState) {
        if (state.isLoading) {
            Log.d("SplashActivity", "isLoading")
        }
        state.error?.let {
            //show error
            Log.d("SplashActivity", "error " + it.localizedMessage)
        }
        state.data?.let {
            Log.d("SplashActivity", "data " + it.size)
        }
    }

}
