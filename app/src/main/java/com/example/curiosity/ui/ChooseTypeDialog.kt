package com.example.curiosity.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val currentType = rememberSaveable {
        mutableStateOf( state.modules[state.listIndex].moduleType )
    }

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        onDismissRequest = { onEvent(ModuleEvent.HideTypeDialog) },
        confirmButton = { TypeButtons( type = currentType.value, onEvent = onEvent)  },
        dismissButton = { TypeButtons( type = "", onEvent = onEvent, dismiss = true) },
        title = { Text(text = "Choose module type:") },
        text  = {
            Column (
                modifier = Modifier
                    .padding(40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                moduleType.forEach { item ->
                    val value = stringResource( id = item.first )
                    Text(
                        text = value,
                        fontSize = 28.sp,
                        color = checkType(currentType.value, value, false),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(35.dp)
                            .clickable { currentType.value = value }
                            .background(
                                color = checkType( currentType.value, value, true ),
                                shape = RoundedCornerShape(16))
                    )
                }
                Log.i("Type", state.moduleType)
            }
        }
    )
}

/**
 * Returns color based on module's topic.
 */
@Composable
fun checkType( currentType: String, value: String, isBackground: Boolean ): Color
{
    if ( isBackground )
    {
        return if ( currentType == value )
        {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.primary
        }
    } else
    {
        return if ( currentType == value )
        {
            MaterialTheme.colorScheme.onSecondary
        } else {
            MaterialTheme.colorScheme.onPrimary
        }
    }
}