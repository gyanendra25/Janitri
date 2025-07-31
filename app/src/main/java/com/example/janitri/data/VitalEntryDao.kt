package com.example.janitri.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalEntryDao {
    @Query("SELECT * FROM vital_entries ORDER BY timestamp DESC")
    fun getAllVitalEntries(): Flow<List<VitalEntry>>

    @Insert
    suspend fun insertVitalEntry(entry: VitalEntry)

    @Query("DELETE FROM vital_entries WHERE id = :id")
    suspend fun deleteVitalEntry(id: Long)
}