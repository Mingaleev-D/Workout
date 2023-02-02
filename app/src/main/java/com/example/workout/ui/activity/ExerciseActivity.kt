package com.example.workout.ui.activity

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.R
import com.example.workout.common.Constants
import com.example.workout.data.model.ExerciseModel
import com.example.workout.databinding.ActivityExerciseBinding
import com.example.workout.databinding.DialogCustomBackConfirmationBinding
import com.example.workout.ui.adapter.ExerciseStatusAdapter
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

   private val binding by lazy { ActivityExerciseBinding.inflate(layoutInflater) }

   private var restTimer: CountDownTimer? = null
   private var restProgress = 0
   private var restTimerDuration:Long = 1

   private var exerciseTimer: CountDownTimer? = null
   private var exerciseProgress = 0
   private var exerciseTimerDuration:Long = 1

   private var exerciseList: ArrayList<ExerciseModel>? = null
   private var currentExercisePosition = -1

   private var tts: TextToSpeech? = null
   private var player: MediaPlayer? = null

   private var exerciseAdapter:ExerciseStatusAdapter? = null

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
         customDialogForBackButton()

      }

      setupStartRestView()

      setupStatusRecyclerView()
   }
   override fun onBackPressed() {
      customDialogForBackButton()

   }
   private fun customDialogForBackButton() {
      val customDialog = Dialog(this)
      val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
      customDialog.setContentView(dialogBinding.root)
      customDialog.setCanceledOnTouchOutside(false)
      dialogBinding.apply {
         yesBtn.setOnClickListener {
         this@ExerciseActivity.finish()
            customDialog.dismiss()
         }
         noBtn.setOnClickListener {
         customDialog.dismiss()
         }
         customDialog.show()
      }
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

      speakUot("Приготовся предстоящее упражнение ${exerciseList!![currentExercisePosition + 1].getName()}")

      if (restTimer != null) {
         restTimer?.cancel()
         restProgress = 0

      }
      setRestProgressBar()
   }

   private fun setRestProgressBar() {
      binding.progressbar.progress = restProgress

      restTimer = object : CountDownTimer(restTimerDuration * 5_000, 1000) {
         override fun onTick(millisUntilFinished: Long) {
            restProgress++
            binding.progressbar.progress = 5 - restProgress
            binding.timerTv.text = (5 - restProgress).toString()
         }

         override fun onFinish() {
            currentExercisePosition++

            exerciseList!![currentExercisePosition].setIsSelected(true)
            exerciseAdapter!!.notifyDataSetChanged()

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
      speakUot("Начинаем ${exerciseList!![currentExercisePosition].getName()}")

      binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
      binding.exerciseNameTv.text = exerciseList!![currentExercisePosition].getName()

      setExerciseProgressBar()
   }

   private fun setExerciseProgressBar() {
      binding.progressbarEx.progress = exerciseProgress

      exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 30_000, 1000) {
         override fun onTick(millisUntilFinished: Long) {
            exerciseProgress++
            binding.progressbarEx.progress = 30 - exerciseProgress
            binding.timerTvEx.text = (30 - exerciseProgress).toString()
         }

         override fun onFinish() {

            if (currentExercisePosition < exerciseList?.size!! - 1) {

               exerciseList!![currentExercisePosition].setIsSelected(false)
               exerciseList!![currentExercisePosition].setIsCompleted(true)
               exerciseAdapter!!.notifyDataSetChanged()

               setupStartRestView()
            } else {
               finish()
               val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
               startActivity(intent)
            }

         }

      }.start()
   }
   private fun setupStatusRecyclerView(){
      exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
      binding.rvExercise.adapter = exerciseAdapter
   }

   override fun onInit(status: Int) {
      val locale = Locale("ru")
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