package com.demo.lottery.models.mvi

sealed class TrendingActionState {
    data class CalculateTrending(val data: List<Pair<Int, Int>>): TrendingActionState()
}