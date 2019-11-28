package com.demo.lottery.models.mvi

import com.demo.lottery.models.Lottery

class DeepLinkViewState(
    val isLoading:Boolean = true,
    val lotteryResult: Lottery? = null,
    val error:Throwable? = null
) {
    fun copy(): Builder {
        return Builder(this)
    }
    class Builder(deepLinkViewState: DeepLinkViewState) {
        private var isLoading = deepLinkViewState.isLoading
        private var lotteryResult = deepLinkViewState.lotteryResult
        private var error = deepLinkViewState.error

        fun loading(loading: Boolean): Builder {
            this.isLoading = loading
            return this
        }

        fun lotteryResult(lotteryResult: Lottery?): Builder {
            this.lotteryResult = lotteryResult
            return this
        }

        fun error(error: Throwable?): Builder {
            this.error = error
            return this
        }

        fun build(): DeepLinkViewState {
            return DeepLinkViewState(isLoading, lotteryResult, error)
        }
    }

}