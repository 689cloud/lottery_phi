package com.demo.lottery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.demo.lottery.helpers.Session
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val session: Session by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        session.dataHistory?.let {
            Log.d("PHIMAI", "size " + it.size)
        }

    }
}
