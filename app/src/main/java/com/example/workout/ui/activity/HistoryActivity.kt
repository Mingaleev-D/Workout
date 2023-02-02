package com.example.workout.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.workout.core.WorkOutApp
import com.example.workout.data.local.db.HistoryDao
import com.example.workout.databinding.ActivityHistoryBinding
import com.example.workout.ui.adapter.HistoryAdapter
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

   private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater) }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setSupportActionBar(binding.toolbarHistoryActivity)
      if (supportActionBar != null) {
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }
      binding.toolbarHistoryActivity.setNavigationOnClickListener {
         onBackPressed()
      }
      val historyDao = (application as WorkOutApp).db.historyDao()
      getAllCompleteDates(historyDao)
   }

   private fun getAllCompleteDates(historyDao: HistoryDao){

      lifecycleScope.launch {
         historyDao.fetchAllDates().collect{ allCompletedDatesList ->
           if(allCompletedDatesList.isNotEmpty()){
              binding.apply {
                 noDataTv.visibility = View.GONE
                 historyRv.visibility = View.VISIBLE
              }

              val dates = ArrayList<String>()
              for (date in allCompletedDatesList){
                 dates.add(date.date)
              }
              val historyAdapter = HistoryAdapter(dates)
              binding.historyRv.adapter
           }else{
              binding.apply {
                 noDataTv.visibility = View.VISIBLE
                 historyRv.visibility = View.GONE
              }
           }
         }
      }
   }
}