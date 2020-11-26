package com.kaisho.inomcrm.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.databinding.PlanListItemBinding

class PlanAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var planList = emptyList<DataBasePOJO>()

    fun newList(list: List<DataBasePOJO>){
        this.planList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlanViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return planList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlanViewHolder){
            holder.bind(planList[position])
        }
    }

    private class PlanViewHolder(private val binding: PlanListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dataBasePOJO: DataBasePOJO){
            binding.model = dataBasePOJO
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): PlanViewHolder{
                val binding = PlanListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PlanViewHolder(binding)
            }
        }
    }
}