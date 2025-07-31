package com.example.janitri.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.janitri.R
import com.example.janitri.data.VitalEntry
import com.example.janitri.ui.theme.PregnancyColors
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun VitalEntryCard(
    vitalEntry: VitalEntry,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PregnancyColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 17.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VitalItem(
                    icon = painterResource(R.drawable.ic_bpm),
                    value = "${vitalEntry.heartRate} bpm",
                    modifier = Modifier.weight(1f)
                )
                VitalItem(
                    icon = painterResource(R.drawable.ic_pressure),
                    value = "${vitalEntry.systolicBP}/${vitalEntry.diastolicBP} mmHg",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VitalItem(
                    icon = painterResource(R.drawable.ic_weight),
                    value = "${vitalEntry.weight.toInt()} kg",
                    modifier = Modifier.weight(1f)
                )
                VitalItem(
                    icon = painterResource(R.drawable.ic_kicks),
                    value = "${vitalEntry.babyKicks} bpm",
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PregnancyColors.PrimaryPurple
                )
                .padding(9.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = formatTimesStamp(vitalEntry.timestamp),
                color = PregnancyColors.TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Composable
private fun VitalItem(
    icon: Painter,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
                .width(22.dp)
                .height(22.dp),
        )
        Text(
            text = value,
            color = PregnancyColors.PrimaryPurpleDeep,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

private fun formatTimesStamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEE, dd MMM yyyy hh:mm a", java.util.Locale.getDefault())
    return sdf.format(Date(timestamp))
}
