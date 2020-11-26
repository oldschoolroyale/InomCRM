package com.kaisho.inomcrm.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.room.viewModel.DatabaseViewModel
import com.kaisho.inomcrm.databinding.DataBaseListItemBinding

class DataBaseAdapter(private val databaseViewModel: DatabaseViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList = emptyList<DataBasePOJO>()

    fun newList(newList: List<DataBasePOJO>){
        this.dataList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DataBaseViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataBaseViewHolder){
            holder.bind(dataList[position], databaseViewModel)
        }
    }

    private class DataBaseViewHolder(private val binding: DataBaseListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: DataBasePOJO, databaseViewModel: DatabaseViewModel){
            databaseViewModel.dataPOJO = model
            binding.viewModel = databaseViewModel
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): DataBaseViewHolder{
                val binding = DataBaseListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return DataBaseViewHolder(binding)
            }
        }
    }
}