package com.example.janitri.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.janitri.data.VitalEntry
import com.example.janitri.data.VitalsDatabase
import com.example.janitri.data.VitalsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VitalsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VitalsRepository = run {
        val database = VitalsDatabase.getDatabase(application)
        VitalsRepository(database.vitalEntryDao())
    }

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    val vitalEntries = repository.getAllVitalEntries()

    fun showAddVitalsDialog() {
        _showDialog.value = true
    }

    fun hideAddVitalsDialog() {
        _showDialog.value = false
    }

    fun addVitalsEntry(
        systolicBP: Int,
        diastolicBP: Int,
        heartRate: Int,
        weight: Double,
        babyKicks: Int
    ) {

        viewModelScope.launch {
            val vitalEntry = VitalEntry(
                systolicBP = systolicBP,
                diastolicBP = diastolicBP,
                heartRate = heartRate,
                weight = weight,
                babyKicks = babyKicks
            )
            repository.insertVitalEntry(vitalEntry)
            hideAddVitalsDialog()
        }
    }
}