package com.example.janitri

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.example.janitri.notification.ReminderManager
import com.example.janitri.ui.VitalsViewModel
import com.example.janitri.ui.components.AddVitalsDialog
import com.example.janitri.ui.components.VitalEntryCard
import com.example.janitri.ui.theme.JanitriTheme
import com.example.janitri.ui.theme.PregnancyColors

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) setupReminders()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                setupReminders()
            }
        } else {
            setupReminders()
        }

        setContent {
            JanitriTheme {
                VitalsTrackingScreen(
                    shouldOpenAddDialog = intent.getBooleanExtra("open_add_vitals", false)
                )
            }
        }
    }

    private fun setupReminders() {
        val reminderManager = ReminderManager(this)
        reminderManager.scheduleVitalsReminders()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsTrackingScreen(
    viewModel: VitalsViewModel = viewModel(),
    shouldOpenAddDialog: Boolean = false
) {

    val vitalEntries by viewModel.vitalEntries.collectAsState(initial = emptyList())
    val showDialog by viewModel.showDialog.collectAsState()

    LaunchedEffect(shouldOpenAddDialog) {
        if (shouldOpenAddDialog) {
            viewModel.showAddVitalsDialog()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Track My Pregnancy",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PregnancyColors.TopBarText
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PregnancyColors.TopBarBackground
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddVitalsDialog() },
                containerColor = PregnancyColors.PrimaryPurple,
                contentColor = PregnancyColors.TextWhite,
                shape = CircleShape
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Vitals"
                )
            }
        }, containerColor = PregnancyColors.BackgroundLight

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            if (vitalEntries.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No Vitals Entries",
                            fontSize = 18.sp,
                            color = PregnancyColors.TextGray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Tap the '+' button to add a new entry",
                            fontSize = 15.sp,
                            color = PregnancyColors.TextGray
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(vitalEntries) { vitalEntries ->
                        VitalEntryCard(vitalEntry = vitalEntries)
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddVitalsDialog(
            onDismiss = { viewModel.hideAddVitalsDialog() },
            onSubmit = { sys, dia, hr, weight, kicks ->
                viewModel.addVitalsEntry(sys, dia, hr, weight, kicks)
            }
        )
    }

}



