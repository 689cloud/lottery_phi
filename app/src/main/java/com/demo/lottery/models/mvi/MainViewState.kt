package com.demo.lottery.models.mvi

import com.demo.lottery.models.Lottery

enum class Prize {
    FIRST , SECOND, THIRD, FOURTH, FIFTH, NO
}

class MainViewState (
    val isLoadingLotteryResult: Boolean = false,
    val lotteryResult: Lottery? = null,
    val lotteryGenerated: Lottery? = null,
    val prize: Prize = Prize.NO,
    val error: Throwable? = null
){
    fun copy(): Builder {
        return Builder(this)
    }

    class Builder(state: MainViewState) {
        private var isLoadingLotteryResult = state.isLoadingLotteryResult
        private var lotteryResult = state.lotteryResult
        private var lotteryGenerated = state.lotteryGenerated
        private var prize = state.prize
        private var error = state.error

        fun isLoadingLotteryResult(isLoadingLotteryResult: Boolean): Builder {
            this.isLoadingLotteryResult = isLoadingLotteryResult
            return this
        }

        fun lotteryResult(lotteryResult: Lottery?): Builder {
            this.lotteryResult = lotteryResult
            return this
        }

        fun lotteryGenerated(lotteryGenerated: Lottery?): Builder {
            this.lotteryGenerated = lotteryGenerated
            return this
        }

        fun prize(prize: Prize): Builder {
            this.prize = prize
            return this
        }
        fun error(error: Throwable?): Builder {
            this.error = error
            return this
        }

        fun build(): MainViewState {
            return MainViewState(isLoadingLotteryResult, lotteryResult, lotteryGenerated, prize)
        }
    }
}
