package com.example.curiosity.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        properties = DialogProperties( usePlatformDefaultWidth = false )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape( 0.dp ),
            color = Color.Yellow
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField( value = state.moduleName, onValueChange = {
                    onEvent( ModuleEvent.SetModuleName( it ) )
                },
                    label = {
                        Text(text = "Module name")
                    })

                //Box for changing type of module.
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .clickable { onEvent(ModuleEvent.ShowTypeDialog) }
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "ArrowDropDown"
                    )
                }
                //TextField for changing type of module.
                TextField(value = state.topic, onValueChange = {
                    onEvent(ModuleEvent.SetTopic( it ) )
                },
                    label = {
                        Text( text = "MQTT topic" )
                    }
                )

                Row {
                    Button(
                        onClick = { onEvent(ModuleEvent.HideSettingDialog) },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Close")
                    }

                    Button(
                        onClick = {
                            onEvent(ModuleEvent.SaveModule)
                                  },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Save")
                    }
                }
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
