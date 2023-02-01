package com.example.workout.ui

import android.content.ContentValues.TAG
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.R
import com.example.workout.common.Constants
import com.example.workout.databinding.ActivityExerciseBinding
import com.example.workout.model.ExerciseModel
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

   private val binding by lazy { ActivityExerciseBinding.inflate(layoutInflater) }

   private var restTimer: CountDownTimer? = null
   private var restProgress = 0

   private var exerciseTimer: CountDownTimer? = null
   private var exerciseProgress = 0

   private var exerciseList: ArrayList<ExerciseModel>? = null
   private var currentExercisePosition = -1

   private var tts: TextToSpeech? = null
   private var player: MediaPlayer? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setSupportActionBar(binding.toolbarExercise)

      if (supportActionBar != null) {
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }

      exerciseList = Constants.defaultExerciseList()

      tts = TextToSpeech(this, this)

      binding.toolbarExercise.setNavigationOnClickListener {
         onBackPressed()
      }

      setupStartRestView()
   }

   private fun setupStartRestView() {

      try {
         val soundURI = Uri
            .parse("android.resource://com.example.workout/" + R.raw.press_start)
         player = MediaPlayer.create(applicationContext,soundURI)
         player?.isLooping = false
         player?.start()
      }catch (e: Exception){
         e.printStackTrace()
      }

      binding.apply {
         flExerciseView.visibility = View.INVISIBLE
         ivImage.visibility = View.INVISIBLE
         exerciseNameTv.visibility = View.INVISIBLE

         titleTv.visibility = View.VISIBLE
         flStartRestView.visibility = View.VISIBLE

         upcomingExerciseNameTv.visibility = View.VISIBLE
         upcomingExerciseLabelTv.visibility = View.VISIBLE
         upcomingExerciseLabelTv.text = exerciseList!![currentExercisePosition + 1].getName()
      }

      speakUot(exerciseList!![currentExercisePosition + 1].getName())

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
         flStartRestView.visibility = View.INVISIBLE

         ivImage.visibility = View.VISIBLE
         exerciseNameTv.visibility = View.VISIBLE
         flExerciseView.visibility = View.VISIBLE

         upcomingExerciseNameTv.visibility = View.INVISIBLE
         upcomingExerciseLabelTv.visibility = View.INVISIBLE
      }


      if (exerciseTimer != null) {
         exerciseTimer?.cancel()
         exerciseProgress = 0
      }
      speakUot(exerciseList!![currentExercisePosition].getName())

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

   override fun onInit(status: Int) {
      val locale = Locale("ru")
     // locale.language
      if(status == TextToSpeech.SUCCESS){
         val result = tts?.setLanguage(locale)

         if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
            Log.e(TAG, "onInit: The language specified is not supported!", )
         }
      }else{
         Log.e(TAG, "TTS: Initialization failed!", )
      }
   }

   private fun speakUot(text: String) {
      tts!!.speak(text, TextToSpeech.QUEUE_ADD, null, "")
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

      if (tts != null){
         tts!!.stop()
         tts!!.shutdown()
      }

      if(player != null){
         player!!.stop()
      }
   }
}