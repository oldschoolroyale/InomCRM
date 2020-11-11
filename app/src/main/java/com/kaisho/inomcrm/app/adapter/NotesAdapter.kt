package com.kaisho.inomcrm.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaisho.inomcrm.app.model.NotePOJO
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import com.kaisho.inomcrm.databinding.NotesListItemBinding

class NotesAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var noteArrayList = ArrayList<NotePOJO>()

    fun newArray(newArray: ArrayList<NotePOJO>){
        this.noteArrayList = newArray
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return noteArrayList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NoteViewHolder){
            holder.bind(noteArrayList[position])
        }
    }

    private class NoteViewHolder(private val binding: NotesListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(notePOJO: NotePOJO){
            binding.model = notePOJO
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): NoteViewHolder{
                val binding = NotesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return NoteViewHolder(binding)
            }
        }
    }
}