package com.example.workout.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.databinding.ActivityMainBinding
import com.example.workout.ui.activity.BMIActivity
import com.example.workout.ui.activity.ExerciseActivity
import com.example.workout.ui.activity.HistoryActivity

class MainActivity : AppCompatActivity() {

   private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)
      
      binding.flStart.setOnClickListener {
         val intent = Intent(this, ExerciseActivity::class.java)
         startActivity(intent)
      }
      binding.flBMI.setOnClickListener {
         val intent = Intent(this, BMIActivity::class.java)
         startActivity(intent)
      }
      binding.flHistory.setOnClickListener {
         val intent = Intent(this, HistoryActivity::class.java)
         startActivity(intent)
      }
   }
}