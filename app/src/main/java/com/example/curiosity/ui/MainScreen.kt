package com.example.curiosity.ui

import DataStoreManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.curiosity.R
import com.example.curiosity.ui.datasource.DataSource

@Composable
fun MainScreen( onButtonClicked: () -> Unit = {})
{
    //context
    val context = LocalContext.current
    // a coroutine scope
    val scope = rememberCoroutineScope()
    // we instantiate the dataStore class
    val dataStore = DataStoreManager(context)

    //state for show settings dialog
    var showSettDialog by remember { mutableStateOf(true) }
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Widget(
            name = "Alarm",
            onNameClicked = { /*TODO*/ },
            onHamMenuClicked = { /*TODO*/ },
            bcgColor = Color.Cyan,
            modifier = Modifier
        )
        for (module in 1..dataStore.readAlarmCount()) {
            Widget(
                name = "Alarm",
                onNameClicked = { /*TODO*/ },
                onHamMenuClicked = { showSettDialog = true },
                bcgColor = Color.Cyan,
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
            ){
            AddButton(onButtonClicked = onButtonClicked )
        }
    }
}

@Composable
fun Widget(
    name: String,
    onNameClicked: () -> Unit,
    onHamMenuClicked: () -> Unit,
    bcgColor: Color,
    modifier: Modifier
)
{
    val iconID = chooseIcon( name = name )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = bcgColor)
    )
    {
        //Pictogram of module
        Image(
            painter = painterResource(id = iconID),
            contentDescription = "Pictogram of Alarm",
            modifier =
            Modifier.padding(16.dp)
        )
        //Module name
        Column {
            Text(
                text = "Module's name",
                fontSize = 28.sp,
                modifier = Modifier
            )
            
            Text(
                text = "Alarm",
                fontSize = 28.sp,
                modifier = Modifier.clickable { onNameClicked }
            )
        }

        Spacer(Modifier.weight(1f))

        //Pictogram informing about connection
        Image(
            painter = painterResource(id = R.drawable.baseline_leak_remove_24),
            contentDescription = "Pictogram informing about connection",
            modifier = Modifier.size(40.dp)
        )

        Spacer(Modifier.weight(0.1f))
        //option menu
        Image(
            painter = painterResource(id = R.drawable.baseline_density_medium_24),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .padding(start = 0.dp, end = 24.dp)
                .clickable { onHamMenuClicked }
        )
    }
}

@Composable
fun AddButton( onButtonClicked: () -> Unit )
{

        Image(painter = painterResource(
            id = R.drawable.baseline_add_circle_24),
            contentDescription = null,
            modifier = Modifier
                .clickable { onButtonClicked() }
                .size(70.dp)
        )

}

/**
 * Function which create UI for picking time.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker( ) {
    val timePickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 1,
        is24Hour = true )

    TimePicker( state = timePickerState )

    Text(text = "Selected H:M = ${timePickerState.hour} : ${timePickerState.minute}")
}




/**
 * Function which choose wright icon to name of module.
 */
@Composable
fun chooseIcon( name:String ): Int
{
    var iconID = 0
    when (name)
    {
        stringResource(id = DataSource.modules[0].first) -> iconID = DataSource.modules[0].second
        stringResource(id = DataSource.modules[1].first) -> iconID = DataSource.modules[1].second
        stringResource(id = DataSource.modules[2].first) -> iconID = DataSource.modules[2].second
        stringResource(id = DataSource.modules[3].first) -> iconID = DataSource.modules[3].second
    }
    return iconID
}

@Composable
@Preview (showSystemUi = true)
fun MyUIPreview()
{
    MainScreen()
}