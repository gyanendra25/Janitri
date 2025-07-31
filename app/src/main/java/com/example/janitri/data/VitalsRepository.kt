package com.example.janitri.data

import kotlinx.coroutines.flow.Flow

class VitalsRepository(private val vitalEntryDao: VitalEntryDao) {

    fun getAllVitalEntries(): Flow<List<VitalEntry>> {
        return vitalEntryDao.getAllVitalEntries()
    }

    suspend fun insertVitalEntry(vitalEntry: VitalEntry) {
        vitalEntryDao.insertVitalEntry(vitalEntry)
    }

}