package com.example.noteapp.aye.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.aye.R
import com.example.noteapp.aye.data.Priority
import com.example.noteapp.aye.databinding.RowLayoutBinding
import com.example.noteapp.aye.room_db.note_table.Note

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<Note>()

    class MyViewHolder(val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.titleTxt.text = dataList[position].title
        holder.binding.descriptionTxt.text = dataList[position].description
        holder.binding.rowBackground.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }

        when (dataList[position].priority) {
            Priority.High -> holder.binding.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )
            Priority.Medium -> holder.binding.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.yellow
                )
            )
            Priority.Low -> holder.binding.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )
        }
    }

    fun setData(note: List<Note>) {
        this.dataList = note
        notifyDataSetChanged()
    }
}