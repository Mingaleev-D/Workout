package com.example.workout.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.workout.R
import com.example.workout.databinding.ItemHistoryRowBinding

/**
 * @author : Mingaleev D
 * @data : 2/02/2023
 */

class HistoryAdapter(
    private val items: ArrayList<String>
) : Adapter<HistoryAdapter.MyHistoryViewHolder>() {

   inner class MyHistoryViewHolder(binding: ItemHistoryRowBinding) : ViewHolder(binding.root) {
      val llHistoryItemMain = binding.historyItemMainLl
      val tvItem = binding.dateItemTv
      val tvPosition = binding.positionTv
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHistoryViewHolder {
      return MyHistoryViewHolder(ItemHistoryRowBinding.inflate(
          LayoutInflater.from(parent.context), parent, false))
   }

   override fun onBindViewHolder(holder: MyHistoryViewHolder, position: Int) {
      val date = items[position]
      holder.tvPosition.text = (position + 1).toString()
      holder.tvItem.text = date

      if(position % 2 == 0){
         holder.llHistoryItemMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,
         R.color.light_green))
      }else{
         holder.llHistoryItemMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,
             R.color.light_gray))
      }
   }

   override fun getItemCount() = items.size
}