package com.example.apimasjid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(val slideList: ArrayList<NewRecycle>): RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val slide: NewRecycle = slideList[position]
        holder?.txtJudulSlide?.text = slide.judul
        holder?.txtUrlSlide?.text = slide.url
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(R.layout.recycle_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return slideList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtJudulSlide = itemView.findViewById(R.id.judulSlideshow) as TextView
        val txtUrlSlide = itemView.findViewById(R.id.urlSlideshow) as TextView
    }
}