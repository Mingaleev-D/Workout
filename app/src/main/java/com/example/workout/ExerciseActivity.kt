package com.example.workout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.databinding.ActivityExerciseBinding
import com.google.android.material.snackbar.Snackbar

class ExerciseActivity : AppCompatActivity() {

   private val binding by lazy { ActivityExerciseBinding.inflate(layoutInflater) }
   private var restTimer:CountDownTimer? = null
   private var restProgress = 0
   private var exerciseTimer:CountDownTimer? = null
   private var exerciseProgress = 0

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

      restTimer = object : CountDownTimer(5_000,1000){
         override fun onTick(millisUntilFinished: Long) {
            restProgress++
            binding.progressbar.progress = 5 - restProgress
            binding.timerTv.text = (5 - restProgress).toString()
         }

         override fun onFinish() {
           // Snackbar.make(binding.root,"Here now we will start the exercise",Snackbar.LENGTH_LONG).show()
            setupExerciseView()
         }

      }.start()
   }
   private fun setupExerciseView() {

      binding.flProgressBar.visibility = View.INVISIBLE
      binding.flExerciseView.visibility = View.VISIBLE
      binding.titleTv.text = "Exercise ...."

      if(exerciseTimer!= null){
         exerciseTimer?.cancel()
         exerciseProgress = 0
      }
      setExerciseProgressBar()
   }

   private fun setExerciseProgressBar(){
      binding.progressbarEx.progress = exerciseProgress

      exerciseTimer = object : CountDownTimer(30_000,1000){
         override fun onTick(millisUntilFinished: Long) {
            exerciseProgress++
            binding.progressbarEx.progress = 30 - exerciseProgress
            binding.timerTvEx.text = (30 - exerciseProgress).toString()
         }

         override fun onFinish() {
            Snackbar.make(binding.root,"30 second are over, let's go to the rest view",Snackbar.LENGTH_LONG).show()
         }

      }.start()
   }

   override fun onDestroy() {
      super.onDestroy()

      if(restTimer!= null){
         restTimer?.cancel()
         restProgress = 0
      }
      if(exerciseTimer!= null){
         exerciseTimer?.cancel()
         exerciseProgress = 0
      }
   }
}