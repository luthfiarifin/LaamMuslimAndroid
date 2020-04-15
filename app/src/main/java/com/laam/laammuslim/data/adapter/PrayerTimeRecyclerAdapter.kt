package com.laam.laammuslim.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laam.laammuslim.R
import com.laam.laammuslim.data.model.ScheduleOfPray
import kotlinx.android.synthetic.main.item_prayer_time.view.*

class PrayerTimeRecyclerAdapter(
    val list: List<ScheduleOfPray>
) : RecyclerView.Adapter<PrayerTimeRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_prayer_time, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val item = list[position]

        view.apply {
            tv_pray_time_fajr.text = item.fajr.toUpperCase()
            tv_pray_time_shurooq.text = item.shurooq.toUpperCase()
            tv_pray_time_dhuhr.text = item.dhuhr.toUpperCase()
            tv_pray_time_asr.text = item.asr.toUpperCase()
            tv_pray_time_maghrib.text = item.maghrib.toUpperCase()
            tv_pray_time_isha.text = item.isha.toUpperCase()
        }
    }
}
