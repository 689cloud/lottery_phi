package com.demo.lottery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.lottery.R
import com.demo.lottery.models.Lottery
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryAdapter(private val listLotto: List<Lottery>): RecyclerView.Adapter<HistoryAdapter.HistoryVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
        return HistoryVH(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))
    }

    override fun getItemCount(): Int {
        return  listLotto.size
    }

    override fun onBindViewHolder(holder: HistoryVH, position: Int) {
        holder.itemView.tvDate.text = listLotto[position].drwNoDate

        holder.itemView.tv1.text = listLotto[position].drwtNo1.toString()
        holder.itemView.tv2.text = listLotto[position].drwtNo2.toString()
        holder.itemView.tv3.text = listLotto[position].drwtNo3.toString()
        holder.itemView.tv4.text = listLotto[position].drwtNo4.toString()
        holder.itemView.tv5.text = listLotto[position].drwtNo5.toString()
        holder.itemView.tv6.text = listLotto[position].drwtNo6.toString()

        holder.itemView.tvBonus.text = listLotto[position].bnusNo.toString()
    }

    class HistoryVH(itemView: View) : RecyclerView.ViewHolder(itemView)
}