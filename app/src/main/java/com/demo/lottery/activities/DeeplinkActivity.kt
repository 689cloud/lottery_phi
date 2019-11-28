package com.demo.lottery.activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.demo.lottery.R
import com.demo.lottery.models.mvi.DeepLinkViewState
import com.demo.lottery.presenters.DeeplinkPresenter
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_deeplink.*

class DeeplinkActivity : MviActivity<DeeplinkView, DeeplinkPresenter>(), DeeplinkView {
    var curentData: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deeplink)
        val data: Uri? = intent?.data
        curentData =  data?.pathSegments
    }

    override fun createPresenter(): DeeplinkPresenter {
        return DeeplinkPresenter()
    }

    override fun getLotteryResult(): Observable<Int> {
        curentData?.let {
             return Observable.just(it[0].toInt())
        }
        return Observable.just(0)
    }

    override fun renderData(state: DeepLinkViewState) {
        progressBar.visibility = if(state.isLoading) View.VISIBLE else View.GONE
        tvResult.visibility = if(state.isLoading) View.GONE else View.VISIBLE

        state.lotteryResult?.let {
            tvResult.text = it.toString()
        }

        state.error?.let {
            tvResult.text = it.localizedMessage
        }
    }
}
