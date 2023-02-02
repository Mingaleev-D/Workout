package com.example.workout.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.workout.R
import com.example.workout.databinding.ItemExerciseStatusBinding
import com.example.workout.data.model.ExerciseModel

/**
 * @author : Mingaleev D
 * @data : 1/02/2023
 */

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>) : Adapter<ExerciseStatusAdapter.MyViewHolder>() {

   inner class MyViewHolder(val binding: ItemExerciseStatusBinding) : ViewHolder(binding.root) {
      val tvItem = binding.itemTv
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      return MyViewHolder(ItemExerciseStatusBinding.inflate(
          LayoutInflater.from(parent.context), parent, false))
   }

   override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      val model = items[position]
      holder.tvItem.text = model.getId().toString()

      when {
         //nahodimsay
         model.getIsSelected()  -> {
            holder.tvItem.background = ContextCompat.getDrawable(
                holder.itemView.context, R.drawable.item_circular_color_bg)
            holder.tvItem.resources.getColor(R.color.colorAccent)
         }
         // zavershili
         model.getIsCompleted() -> {
            holder.tvItem.background = ContextCompat.getDrawable(
                holder.itemView.context, R.drawable.item_circular_color_accent_border)
            holder.tvItem.resources.getColor(R.color.black)
         }
         else                   -> {
            holder.tvItem.background = ContextCompat.getDrawable(
                holder.itemView.context, R.drawable.item_circular_color)
            holder.tvItem.resources.getColor(R.color.black)

         }
      }
   }

   override fun getItemCount() = items.size
}