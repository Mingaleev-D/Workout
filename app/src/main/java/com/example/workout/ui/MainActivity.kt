package com.example.workout.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

   private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)
      
      binding.flStart.setOnClickListener {
         val intent = Intent(this, ExerciseActivity::class.java)
         startActivity(intent)

      }
   }
}