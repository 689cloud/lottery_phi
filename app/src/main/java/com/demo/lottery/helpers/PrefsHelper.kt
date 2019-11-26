package com.demo.lottery.helpers

import android.content.Context

class PrefsHelper(
    private val context: Context
) {

    private val SHARED_PREF_NAME = "SHARED_PREF_NAME"
    private val KEY_IS_FIRST_TIME = "KEY_IS_FIRST_TIME"

    fun isFirstTime(): Boolean {
        val prefs = context.getSharedPreferences(SHARED_PREF_NAME, 0)
        val isFirstTime = prefs.getBoolean(KEY_IS_FIRST_TIME, true)
        if (isFirstTime) {
            prefs.edit().putBoolean(KEY_IS_FIRST_TIME, false).apply()
        }
        return isFirstTime
    }


}