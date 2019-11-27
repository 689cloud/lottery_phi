package com.demo.lottery.activities

import android.os.Bundle
import android.view.View
import com.demo.lottery.R
import com.demo.lottery.models.Lottery
import com.demo.lottery.models.mvi.MainViewState
import com.demo.lottery.presenters.MainPresenter
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : MviActivity<MainView, MainPresenter>(), MainView {

    private var currentViewState: MainViewState? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun renderData(state: MainViewState) {
        currentViewState = state
        progressBar.visibility = if (state.isLoadingLotteryResult) View.VISIBLE else View.GONE
        tvPrize.text = ""
        state.lotteryResult?.let {
            tvResult.text = it.toString()
        }
        state.lotteryGenerated?.let {
            tvLottery.text = it.toString()
        }
        state.error?.let {
            tvResult.text = "N/A"
        }
        state.prize?.let {
            tvPrize.text = resources.getString(it.resId)
        }
    }

    override fun getLotteryResult(): Observable<Int> {
        return  svDrwNo.textChanges()
            .skipInitialValue()
            .map{it.toString().toIntOrNull() ?: 0}
            .debounce(300, TimeUnit.MILLISECONDS)
    }

    override fun generateLottery(): Observable<Unit> = btnGenerate.clicks()

    override fun calculateResult(): Observable<Pair<Lottery?, Lottery?>> {
        return btPrize.clicks().map{ currentViewState?.let { it.lotteryGenerated to it.lotteryResult } }
    }



}
