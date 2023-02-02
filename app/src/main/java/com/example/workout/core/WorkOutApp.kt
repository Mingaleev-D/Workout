package com.example.workout.core

import android.app.Application
import com.example.workout.data.local.db.HistoryDatabase

/**
 * @author : Mingaleev D
 * @data : 2/02/2023
 */

class WorkOutApp:Application() {

   val db by lazy {
      HistoryDatabase.getInstance(this)
   }
}