package com.example.curiosity.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curiosity.ui.room.ModuleEvent
import com.example.curiosity.ui.room.ModuleState
import kotlin.reflect.KFunction1

/**
 * Buttons for setting dialog.
 */
@Composable
fun SettingButtons(
    state: ModuleState,
    onEvent: KFunction1<ModuleEvent, Unit>,
    modifier: Modifier = Modifier
        .padding(12.dp)
        .size(width = 110.dp, height = 50.dp)
)
{
    val colors = ButtonColors(
        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.onTertiary,
        disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer)

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ){
        Button(
            onClick = { onEvent( ModuleEvent.HideSettingDialog ) },
            colors = colors,
            modifier = modifier,
        ) {
            Text(
                text = "Close",
                fontSize = 18.sp
                )
        }

        Button(
            onClick = {
                onEvent( ModuleEvent.DeleteModule( state.modules[ state.listIndex ] ) )
                onEvent( ModuleEvent.HideSettingDialog )
            },
            colors = colors,
            modifier = modifier
        ) {
            Text("Delete",fontSize = 18.sp)
        }

        Button(
            onClick = { onEvent(ModuleEvent.SaveModule) },
            colors = colors,
            enabled = savable(state),
            modifier = modifier
        ) {
            Text("Save",fontSize = 18.sp)
        }
    }
}

/**
 * Buttons for setting dialog.
 */
@Composable
fun TypeButtons(
    type: String,
    onEvent: KFunction1<ModuleEvent, Unit>,
    dismiss: Boolean = false
)
{
    if ( dismiss ) {
        Button(
            onClick = { onEvent( ModuleEvent.HideTypeDialog ) },
            colors = ButtonColors(
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text("Close")
        }
    } else {
        Button(
            onClick = {
                onEvent( ModuleEvent.SaveTypeDialog( type ) )

            },
            colors = ButtonColors(
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save")
        }
    }
}

/**
 * Check if main parameters are filled.
 */
fun savable( state: ModuleState ): Boolean
{
    return !( state.moduleName.isBlank() || state.moduleType.isBlank() || state.topic.isBlank() )
}

/**
 * Buttons for Time Picker dialog.
 */
@Composable
fun TimePickerButtons(
    alarmingInfo: String,
    onEvent: KFunction1<ModuleEvent, Unit>,
    dismiss: Boolean = false
)
{
    if ( dismiss ) {
        Button(
            onClick = { onEvent( ModuleEvent.HideTimepicker ) },
            colors = ButtonColors(
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text("Close")
        }
    } else {
        Button(
            onClick = {
                onEvent( ModuleEvent.SetAlarmingInfo( alarmingInfo ) )

            },
            colors = ButtonColors(
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save")
        }
    }
}