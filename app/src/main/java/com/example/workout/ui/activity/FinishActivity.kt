package com.example.workout.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.workout.core.WorkOutApp
import com.example.workout.data.local.db.HistoryDao
import com.example.workout.data.local.db.HistoryEntity
import com.example.workout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
      val historyDao = (application as WorkOutApp).db.historyDao()
      addDateToDatabase(historyDao)
   }
   private fun addDateToDatabase(historyDao: HistoryDao){

      val cal = Calendar.getInstance()
      val dateTime = cal.time

      val sdf = SimpleDateFormat("dd MMM yyyy",Locale.getDefault())
      val date = sdf.format(dateTime)

      lifecycleScope.launch {
         historyDao.insert(HistoryEntity(date))
      }

   }
}