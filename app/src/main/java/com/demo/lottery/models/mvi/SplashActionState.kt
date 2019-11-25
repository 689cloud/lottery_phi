package com.demo.lottery.models.mvi

import com.demo.lottery.models.Lottery

sealed class SplashActionState {
    object LoadingState: SplashActionState()
    data class DataState (val data: List<Lottery>): SplashActionState()
    data class ErrorState(val error: Throwable) : SplashActionState()
}