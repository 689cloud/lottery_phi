package com.demo.lottery.models.mvi

import com.demo.lottery.models.Lottery

class SplashViewState (
    val isLoading: Boolean = false,
    val firstTime: Boolean = false,
    val error: Throwable? = null,
    var data: List<Lottery>? = null
) {
    fun copy(): Builder {
        return Builder(this)
    }

    class Builder(restaurantViewState: SplashViewState) {
        private var isLoading = restaurantViewState.isLoading
        private var isFirstTime = restaurantViewState.firstTime
        private var error = restaurantViewState.error
        private var data = restaurantViewState.data

        fun isLoading(isLoading: Boolean): Builder {
            this.isLoading = isLoading
            return this
        }

        fun error(error: Throwable?): Builder {
            this.error = error
            return this
        }

        fun data(data: List<Lottery>?): Builder {
            this.data = data
            return this
        }

        fun build() : SplashViewState {
            return  SplashViewState(isLoading, isFirstTime, error, data)
        }
    }
}