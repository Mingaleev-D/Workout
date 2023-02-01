package com.example.workout

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.databinding.ActivityExerciseBinding
import com.google.android.material.snackbar.Snackbar

class ExerciseActivity : AppCompatActivity() {

   private val binding by lazy { ActivityExerciseBinding.inflate(layoutInflater) }
   private var restTimer:CountDownTimer? = null
   private var restProgress = 0

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

      setupRestView()
   }

   private fun setupRestView() {

      if (restTimer != null) {
         restTimer?.cancel()
         restProgress = 0

      }
      setRestProgressBar()
   }

   private fun setRestProgressBar(){
      binding.progressbar.progress = restProgress

      restTimer = object : CountDownTimer(10_000,1000){
         override fun onTick(millisUntilFinished: Long) {
            restProgress++
            binding.progressbar.progress = 10 - restProgress
            binding.timerTv.text = (10 - restProgress).toString()
         }

         override fun onFinish() {
            Snackbar.make(binding.root,"Here now we will start the exercise",Snackbar.LENGTH_LONG).show()
         }

      }.start()
   }

   override fun onDestroy() {
      super.onDestroy()

      if(restTimer!= null){
         restTimer?.cancel()
         restProgress = 0
      }
   }
}