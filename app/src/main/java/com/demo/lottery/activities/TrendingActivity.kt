package com.demo.lottery.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.lottery.R
import com.demo.lottery.helpers.Session
import com.demo.lottery.models.Lottery
import com.demo.lottery.models.mvi.TrendingViewState
import com.demo.lottery.presenters.TrendingPresenter
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_trending.*
import org.koin.android.ext.android.inject

class TrendingActivity : MviActivity<TrendingView, TrendingPresenter>(), TrendingView {
    val session:Session by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)
        btBack.setOnClickListener { finish() }
    }

    override fun createPresenter(): TrendingPresenter {
        return TrendingPresenter()
    }

    override fun calculateTrending(): Observable<List<Lottery>> {
        return Observable.just(session.dataHistory)
    }

    override fun renderTrending(state: TrendingViewState) {
        state.trendingNumber?.let {
            var stringToShow = ""
            it.forEach {item ->
                stringToShow += String.format("%d(%d),    ", item.first, item.second)
            }
            tvResult.text = stringToShow
        }
    }
}
