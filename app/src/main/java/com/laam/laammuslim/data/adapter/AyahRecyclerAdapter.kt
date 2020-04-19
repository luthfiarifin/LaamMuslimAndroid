package com.laam.laammuslim.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.laam.laammuslim.R
import com.laam.laammuslim.data.db.entities.Ayah
import kotlinx.android.synthetic.main.item_quran_ayah.view.*

class AyahRecyclerAdapter(val fontSize: String?) :
    RecyclerView.Adapter<AyahRecyclerAdapter.ViewHolder>() {

    var list: List<Ayah> = listOf()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quran_ayah, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val item = list[position]

        view.apply {
            tv_item_ayah_verse.text = item.verseID.toString()
            tv_item_ayah_latin.text = item.readText
            tv_item_ayah_arab.text = item.ayahText
            tv_item_ayah_translate.text = item.indoText

            val color = if (position % 2 == 0) {
                R.color.colorGrey1
            } else {
                R.color.colorGrey2
            }
            setBackgroundColor(ContextCompat.getColor(view.context, color))

            fontSize?.let {
                tv_item_ayah_arab.textSize = it.toFloat()
            }
        }
    }

    fun updateList(list: List<Ayah>) {
        this.list = list
        notifyDataSetChanged()
    }
}
