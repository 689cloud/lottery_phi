package com.demo.lottery.helpers

import com.demo.lottery.models.Lottery
import com.demo.lottery.models.mvi.MainActionState
import com.demo.lottery.models.mvi.Prize
import com.demo.lottery.retrofit.LotteryApi
import io.reactivex.Observable
import java.util.*


object LotteryHelper {

    fun getLotteryResult(number : Int): Observable<MainActionState> {
        return LotteryApi.getClient()
            .getLotoResult(no = number)
            .map<MainActionState>{ MainActionState.LotteryResultState(it)}
            .startWith(MainActionState.LoadingState)
            .onErrorReturn { MainActionState.ErrorState(it)}
    }

    fun generateLottery(): Observable<MainActionState> {
        val arrayNumber = List(45){it+1}
        Collections.shuffle(arrayNumber)
        val result = arrayNumber.subList(0, 6).sorted()
        return Observable.just(result)
            .map<MainActionState>{ MainActionState.LotteryRandomState(Lottery("success", -1, it[0], it[1], it[2], it[3], it[4], it[5],-1)) }
    }

    fun calculatePrize(current: Lottery?, result: Lottery?): Observable<MainActionState> {
        if (current == null || result == null) {
            return Observable.just(MainActionState.LotteryPrizeState(Prize.NO))
        } else {
            var countSame = 0
            if (current.drwtNo1 == result.drwtNo1) countSame++
            if (current.drwtNo2 == result.drwtNo2) countSame++
            if (current.drwtNo3 == result.drwtNo3) countSame++
            if (current.drwtNo4 == result.drwtNo4) countSame++
            if (current.drwtNo5 == result.drwtNo5) countSame++
            if (current.drwtNo6 == result.drwtNo6) countSame++

            val hasBonus = current.toArray().contains(result.bnusNo)

            return when (countSame) {
                3 -> Observable.just(MainActionState.LotteryPrizeState(Prize.FIFTH))
                4 -> Observable.just(MainActionState.LotteryPrizeState(Prize.FOURTH))
                5 -> Observable.just(MainActionState.LotteryPrizeState(if (hasBonus) Prize.SECOND else Prize.THIRD))
                6 -> Observable.just(MainActionState.LotteryPrizeState(Prize.FIRST))
                else -> Observable.just(MainActionState.LotteryPrizeState(Prize.NO))
            }
        }
    }
}