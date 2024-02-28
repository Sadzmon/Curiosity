package com.example.curiosity.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.curiosity.ui.room.ModuleEvent
import com.example.curiosity.ui.room.ModuleState
import kotlin.reflect.KFunction1

@Composable
fun AddModuleDialog(
    state: ModuleState,
    onEvent: KFunction1<ModuleEvent, Unit>,
    modifier: Modifier = Modifier
)
{
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(ModuleEvent.HideDialog) },
        title = { Text( text = "Add module" ) },
        text = {
            Column (
                verticalArrangement = Arrangement.spacedBy( 16.dp )
            ){
                TextField(value = state.moduleName, onValueChange = {
                    onEvent( ModuleEvent.SetModuleName( it ) )
                },
                placeholder = {
                    Text(text = "Module name")
                })

                TextField(value = state.moduleType, onValueChange = {
                    onEvent( ModuleEvent.SetModuleType( it ) )
                },
                    placeholder = {
                        Text( text = "Module type" )
                    })

                TextField(value = state.topic, onValueChange = {
                    onEvent(ModuleEvent.SetTopic( it ) )
                },
                    placeholder = {
                        Text( text = "Topic" )
                    })
            }
        },
        dismissButton = {
            TextButton(onClick = { onEvent(ModuleEvent.HideDialog) } ) {
                Text(text = "Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onEvent(ModuleEvent.SaveModule) }) {
                Text(text = "Confirm")
            }
        },
    )
}