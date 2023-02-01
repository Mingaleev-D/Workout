package com.example.workout.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.common.Constants
import com.example.workout.databinding.ActivityExerciseBinding
import com.example.workout.model.ExerciseModel

class ExerciseActivity : AppCompatActivity() {

   private val binding by lazy { ActivityExerciseBinding.inflate(layoutInflater) }

   private var restTimer: CountDownTimer? = null
   private var restProgress = 0

   private var exerciseTimer: CountDownTimer? = null
   private var exerciseProgress = 0

   private var exerciseList: ArrayList<ExerciseModel>? = null
   private var currentExercisePosition = -1

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setSupportActionBar(binding.toolbarExercise)

      if (supportActionBar != null) {
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }

      exerciseList = Constants.defaultExerciseList()

      binding.toolbarExercise.setNavigationOnClickListener {
         onBackPressed()
      }

      setupStartRestView()
   }

   private fun setupStartRestView() {

      binding.apply {
         flExerciseView.visibility = View.INVISIBLE
         ivImage.visibility = View.INVISIBLE
         exerciseNameTv.visibility = View.INVISIBLE

         titleTv.visibility = View.VISIBLE
         flStartRestView.visibility = View.VISIBLE
      }


      if (restTimer != null) {
         restTimer?.cancel()
         restProgress = 0

      }
      setRestProgressBar()
   }

   private fun setRestProgressBar() {
      binding.progressbar.progress = restProgress

      restTimer = object : CountDownTimer(2_000, 1000) {
         override fun onTick(millisUntilFinished: Long) {
            restProgress++
            binding.progressbar.progress = 5 - restProgress
            binding.timerTv.text = (5 - restProgress).toString()
         }

         override fun onFinish() {
            currentExercisePosition++
            setupStartExerciseView()
         }

      }.start()
   }

   private fun setupStartExerciseView() {

      binding.apply {
         titleTv.visibility = View.INVISIBLE
         titleTv.text = "Exercise Name ...."
         flStartRestView.visibility = View.INVISIBLE

         ivImage.visibility = View.VISIBLE
         exerciseNameTv.visibility = View.VISIBLE
         flExerciseView.visibility = View.VISIBLE
      }


      if (exerciseTimer != null) {
         exerciseTimer?.cancel()
         exerciseProgress = 0
      }

      binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
      binding.exerciseNameTv.text = exerciseList!![currentExercisePosition].getName()

      setExerciseProgressBar()
   }

   private fun setExerciseProgressBar() {
      binding.progressbarEx.progress = exerciseProgress

      exerciseTimer = object : CountDownTimer(6_000, 1000) {
         override fun onTick(millisUntilFinished: Long) {
            exerciseProgress++
            binding.progressbarEx.progress = 30 - exerciseProgress
            binding.timerTvEx.text = (30 - exerciseProgress).toString()
         }

         override fun onFinish() {
            if (currentExercisePosition < exerciseList?.size!! - 1) {
               setupStartRestView()
            } else {
               Toast.makeText(this@ExerciseActivity, "you completed the workout", Toast.LENGTH_SHORT).show()
            }

         }

      }.start()
   }

   override fun onDestroy() {
      super.onDestroy()

      if (restTimer != null) {
         restTimer?.cancel()
         restProgress = 0
      }
      if (exerciseTimer != null) {
         exerciseTimer?.cancel()
         exerciseProgress = 0
      }
   }
}