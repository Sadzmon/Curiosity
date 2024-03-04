import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.curiosity.ui.room.ModuleEvent
import com.example.curiosity.ui.room.ModuleState
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog( state: ModuleState, onEvent: KFunction1<ModuleEvent, Unit> ) {
    val alarmingInfo = state.alarmingInfo.split(":")
    var selectedHour by remember { mutableIntStateOf(0 ) }
    var selectedMinute by remember { mutableIntStateOf(0) }

    if ( alarmingInfo.isNotEmpty() )
    {
        selectedHour   = alarmingInfo[0].toInt()
        selectedMinute = alarmingInfo[1].toInt()
    }

    val timeState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute,
        is24Hour = true
    )

        BasicAlertDialog(
            onDismissRequest = { onEvent(ModuleEvent.HideTimepicker) },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = RoundedCornerShape( 20.dp )
                    )
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(
                    state = timeState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        selectorColor = MaterialTheme.colorScheme.primary,
                        clockDialSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        clockDialUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
                        timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
                        periodSelectorBorderColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onEvent(ModuleEvent.HideTimepicker) }) {
                        Text(text = "Close")
                    }
                    TextButton(onClick = {
                        onEvent(ModuleEvent.SetAlarmingInfo(
                            alarmInfo = "$selectedHour:$selectedMinute"
                        ))
                        selectedHour = timeState.hour
                        selectedMinute = timeState.minute
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
}