package com.demo.lottery.helpers

import com.demo.lottery.models.Lottery
import com.demo.lottery.models.mvi.*
import com.demo.lottery.retrofit.LotteryApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object LotteryHelper {

    fun getLotteryDatas(): Observable<SplashActionState> {
        return Observable.fromIterable(List(50) {it + 1})
            .flatMap {
                LotteryApi.getClient()
                    .getLotoResult(no = it)
                    .subscribeOn(Schedulers.io())
            }
            .toSortedList { lottery, lottery2 -> if(lottery.drwNo < lottery2.drwNo) -1 else 1 }
            .toObservable()
            .map<SplashActionState>{
                SplashActionState.DataState(it)}
            .startWith(SplashActionState.LoadingState)
            .onErrorReturn { SplashActionState.ErrorState(it) }
    }

    fun getLotteryResult(number : Int): Observable<MainActionState> {
        return LotteryApi.getClient()
            .getLotoResult(no = number)
            .subscribeOn(Schedulers.io())
            .map<MainActionState>{ MainActionState.LotteryResultState(it)}
            .startWith(MainActionState.LoadingState)
            .onErrorReturn { MainActionState.ErrorState(it)}
    }

    fun getLotteryResultDeeplink(number : Int): Observable<DeeplinkActionState> {
        return LotteryApi.getClient()
            .getLotoResult(no = number)
            .subscribeOn(Schedulers.io())
            .map<DeeplinkActionState>{DeeplinkActionState.LotteryResultState(it)}
            .startWith(DeeplinkActionState.LoadingState)
            .onErrorReturn {DeeplinkActionState.ErrorState(it)}
    }

    fun generateLottery(): Observable<MainActionState> {
        val arrayNumber = MutableList(45){it+1}
        arrayNumber.shuffle()
        val result = arrayNumber.subList(0, 6).sorted()
        return Observable.just(result)
            .map<MainActionState>{ MainActionState.LotteryRandomState(Lottery("success", -1,"", it[0], it[1], it[2], it[3], it[4], it[5],-1)) }
    }

    fun calculatePrize(current: Lottery?, result: Lottery?): Observable<MainActionState> {
        if (current == null || result == null) {
            return Observable.just(MainActionState.LotteryPrizeState(Prize.NO))
        } else {
            var countSame = 0
            val arrayCurrent = current.toArray()

            if (arrayCurrent.contains(result.drwtNo1)) countSame++
            if (arrayCurrent.contains(result.drwtNo2)) countSame++
            if (arrayCurrent.contains(result.drwtNo3)) countSame++
            if (arrayCurrent.contains(result.drwtNo4)) countSame++
            if (arrayCurrent.contains(result.drwtNo5)) countSame++
            if (arrayCurrent.contains(result.drwtNo6)) countSame++

            val hasBonus = arrayCurrent.contains(result.bnusNo)

            return when (countSame) {
                3 -> Observable.just(MainActionState.LotteryPrizeState(Prize.FIFTH))
                4 -> Observable.just(MainActionState.LotteryPrizeState(Prize.FOURTH))
                5 -> Observable.just(MainActionState.LotteryPrizeState(if (hasBonus) Prize.SECOND else Prize.THIRD))
                6 -> Observable.just(MainActionState.LotteryPrizeState(Prize.FIRST))
                else -> Observable.just(MainActionState.LotteryPrizeState(Prize.NO))
            }
        }
    }

    fun calculateTrending(data: List<Lottery>): Observable<TrendingActionState> {
        val result = data
            .flatMap { it.toArrayTrending() }
            .groupBy { it }
            .map { Pair(it.key, it.value.size)}
            .sortedByDescending{it.second}
        return Observable.just(TrendingActionState.CalculateTrending(result))

    }
}