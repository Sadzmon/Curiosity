package com.example.curiosity.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.curiosity.ui.datasource.DataSource
import com.example.curiosity.ui.room.ModuleEvent
import com.example.curiosity.ui.room.ModuleState
import kotlin.reflect.KFunction1

@Composable
fun DialogModuleSettings(
    state: ModuleState,
    onEvent: KFunction1<ModuleEvent, Unit>
) {
    Dialog(
        onDismissRequest = { onEvent(ModuleEvent.HideSettingDialog) },
        properties = DialogProperties( usePlatformDefaultWidth = false ),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //TextField for changing module's name.
                TextField( value = state.moduleName, onValueChange = {
                    onEvent( ModuleEvent.SetModuleName( it ) )
                },
                    label = {
                        Text(text = "Module name")
                    },
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                )

                //TextField for changing type of module.
                TextField(
                    value = state.moduleType,
                    onValueChange = { },
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        onEvent(ModuleEvent.ShowTypeDialog)
                                    }
                                }
                            }
                        },
                    readOnly = true,
                    label = { Text("Module type") },
                    trailingIcon = { Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Localized description"
                    ) },
                    modifier = Modifier
                        .padding( bottom = 16.dp )
                        .clickable { onEvent(ModuleEvent.ShowTypeDialog) },
                )

                //TextField for changing module's topic.
                TextField(value = state.topic, onValueChange = {
                    onEvent(ModuleEvent.SetTopic( it ) )
                },
                    label = {
                        Text( text = "MQTT topic" )
                    },
                    modifier = Modifier.padding( bottom = 16.dp)
                )
                Spacer( modifier = Modifier.weight(1f) )

                SettingButtons( state = state, onEvent = onEvent )
            }
        }
    }
    if ( state.isShowTypeDialog )
    {
        ChooseTypeDialog(
        moduleType = DataSource.modules,
        onEvent = onEvent,
        state = state
        )
    }
}