package com.demo.lottery.models.mvi

import com.demo.lottery.models.Lottery

sealed class MainActionState {
    object LoadingState: MainActionState()
    data class LotteryResultState(val lottery: Lottery) : MainActionState()
    data class LotteryRandomState(val lottery: Lottery) : MainActionState()
    data class LotteryPrizeState(val prize: Prize) : MainActionState()
    data class ErrorState(val error: Throwable) : MainActionState()
}