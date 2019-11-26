package com.demo.lottery.activities

import android.os.Bundle
import com.demo.lottery.R
import com.demo.lottery.models.Lottery
import com.demo.lottery.models.mvi.MainViewState
import com.demo.lottery.presenters.MainPresenter
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MviActivity<MainView, MainPresenter>(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun renderData(state: MainViewState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLotteryResult(): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateLottery(): Observable<Unit> = btnGenerate.clicks()

    override fun calculateResult(): Observable<Pair<Lottery?, Lottery?>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}
