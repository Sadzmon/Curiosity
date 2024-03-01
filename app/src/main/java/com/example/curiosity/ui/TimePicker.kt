package com.example.curiosity.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import com.example.curiosity.ui.room.ModuleEvent
import com.example.curiosity.ui.room.ModuleState

/**
 * Function which create UI for picking time.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker( state: ModuleState ) {

    var hour   = 0
    var minute = 0

    if ( state.alarmingInfo.isNotBlank() )
    {
        val alarmingInfo = state.alarmingInfo.split(":")
        hour = alarmingInfo[0].toInt()
        minute = alarmingInfo[1].toInt()
    }
    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = true )

    TimePicker( state = timePickerState )

    Text(text = "Selected H:M = ${timePickerState.hour} : ${timePickerState.minute}")
}

fun SeperateAlarmInfo( state: ModuleState )
{

    if ( state.alarmingInfo.isBlank() )
    {
        return
    }


}