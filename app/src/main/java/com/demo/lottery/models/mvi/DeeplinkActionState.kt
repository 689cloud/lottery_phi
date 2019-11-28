package com.demo.lottery.models.mvi

import com.demo.lottery.models.Lottery

sealed class DeeplinkActionState {
    object LoadingState: DeeplinkActionState()
    data class LotteryResultState(val lottery: Lottery) : DeeplinkActionState()
    data class ErrorState(val error: Throwable) : DeeplinkActionState()
}