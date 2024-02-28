package com.example.curiosity.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.curiosity.ui.room.ModuleEvent
import com.example.curiosity.ui.room.ModuleState
import kotlin.reflect.KFunction1

@Composable
fun ChooseTypeDialog(
    moduleType: List<Pair<Int, Int>>,
    onEvent: KFunction1<ModuleEvent, Unit>,
    state: ModuleState
)
{
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = { /*TODO*/ },
        title = { Text(text = "Choose module type") },
        text  = { moduleType.forEach { item ->
            val value = stringResource( id = item.first )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEvent(ModuleEvent.SetModuleType( value ) ) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text( text = value )
              }
            }
        }
    )
}