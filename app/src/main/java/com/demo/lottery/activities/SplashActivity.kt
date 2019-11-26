package com.demo.lottery.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.demo.lottery.MainActivity
import com.demo.lottery.R
import com.demo.lottery.models.mvi.SplashViewState
import com.demo.lottery.presenters.SplashPresenter
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : MviActivity<SplashView, SplashPresenter>() , SplashView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        btTryAgain.performClick()
    }

    override fun createPresenter(): SplashPresenter {
        return SplashPresenter()
    }

    override fun emitFirtstLoadData(): Observable<Unit>  = Observable.just(Unit)

    override fun emitLoadData(): Observable<Unit> = btTryAgain.clicks()

    override fun renderData(state: SplashViewState) {
        if (state.isLoading) {
            progressBar.visibility = View.VISIBLE
            btTryAgain.visibility = View.GONE
            tvError.text = ""
        }
        state.error?.let {
            progressBar.visibility = View.GONE
            btTryAgain.visibility = View.VISIBLE
            tvError.text = it.localizedMessage
        }
        state.data?.let {
            val intent = Intent(
                this,
                if (state.firstTime) WelcomeActivity::class.java else MainActivity::class.java
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

}
