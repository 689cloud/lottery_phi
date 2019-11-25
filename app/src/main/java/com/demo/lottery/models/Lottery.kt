package com.demo.lottery.models

import com.squareup.moshi.Json

data class Lottery (
    @field:Json(name = "returnValue") val returnValue: String,
    @field:Json(name = "drwNo") val drwNo: Int,
    @field:Json(name = "drwtNo1") val drwtNo1: Int,
    @field:Json(name = "drwtNo2") val drwtNo2: Int,
    @field:Json(name = "drwtNo3") val drwtNo3: Int,
    @field:Json(name = "drwtNo4") val drwtNo4: Int,
    @field:Json(name = "drwtNo5") val drwtNo5: Int,
    @field:Json(name = "drwtNo6") val drwtNo6: Int,
    @field:Json(name = "bnusNo") val bnusNo: Int
)