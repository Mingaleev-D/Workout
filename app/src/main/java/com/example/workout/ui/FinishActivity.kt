package com.example.workout.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

   private val binding by lazy { ActivityFinishBinding.inflate(layoutInflater) }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setSupportActionBar(binding.toolbarFinishActivity)

      if (supportActionBar != null) {
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }
      binding.toolbarFinishActivity.setNavigationOnClickListener {
         onBackPressed()
      }
      binding.finishBtn.setOnClickListener {
         finish()
      }
   }
}