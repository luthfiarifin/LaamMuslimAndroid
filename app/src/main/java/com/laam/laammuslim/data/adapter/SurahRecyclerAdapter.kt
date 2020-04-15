package com.laam.laammuslim.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laam.laammuslim.R
import com.laam.laammuslim.data.db.entities.Sura
import kotlinx.android.synthetic.main.item_quran_surah.view.*

class SurahRecyclerAdapter(
    val list: List<Sura>
) : RecyclerView.Adapter<SurahRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quran_surah, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val item = list[position]

        view.apply {
            tv_item_surah_verse.text = item.id.toString()
            tv_item_surah_surah.text = item.englishName
            tv_item_surah_arab.text = item.name
            tv_item_surah_total_ayah.text = "${item.revelationType} - ${item.numberOfAyahs} Ayah"
        }
    }
}
