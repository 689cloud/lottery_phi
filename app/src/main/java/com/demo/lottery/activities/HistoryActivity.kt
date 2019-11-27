package com.demo.lottery.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.lottery.R
import com.demo.lottery.adapters.HistoryAdapter
import com.demo.lottery.helpers.Session
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.ext.android.inject

class HistoryActivity : AppCompatActivity() {

    private val session: Session by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        btBack.setOnClickListener { finish() }
        session.dataHistory?.let {
            rvHistory.run {
                layoutManager = LinearLayoutManager(this@HistoryActivity)
                adapter = HistoryAdapter(it)
            }
        }
    }
}
