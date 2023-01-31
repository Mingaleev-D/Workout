package com.example.workout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

   private val binding by lazy { ActivityExerciseBinding.inflate(layoutInflater) }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setSupportActionBar(binding.toolbarExercise)

      if(supportActionBar != null){
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }

      binding.toolbarExercise.setNavigationOnClickListener {
         onBackPressed()
      }
   }
}