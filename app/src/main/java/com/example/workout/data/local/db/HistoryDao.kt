package com.example.workout.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * @author : Mingaleev D
 * @data : 2/02/2023
 */

@Dao
interface HistoryDao {

   @Insert
   suspend fun insert(historyEntity: HistoryEntity)

   @Query("SELECT * FROM `history-table`")
   fun fetchAllDates():Flow<List<HistoryEntity>>
}