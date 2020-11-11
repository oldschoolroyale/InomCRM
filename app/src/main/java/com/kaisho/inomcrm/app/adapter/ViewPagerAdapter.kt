package com.kaisho.inomcrm.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaisho.inomcrm.app.model.ViewPagerPOJO
import com.kaisho.inomcrm.databinding.ViewPagerItemBinding

class ViewPagerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var viewPagerList = ArrayList<ViewPagerPOJO>()

    fun newList(newList: ArrayList<ViewPagerPOJO>){
        this.viewPagerList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewPagerHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return viewPagerList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewPagerHolder){
            holder.bind(viewPagerList[position])
        }
    }

    private class ViewPagerHolder(private val binding: ViewPagerItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(viewPagerPOJO: ViewPagerPOJO){
            binding.model = viewPagerPOJO
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): ViewPagerHolder{
                val bind = ViewPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewPagerHolder(bind)
            }
        }

    }
}