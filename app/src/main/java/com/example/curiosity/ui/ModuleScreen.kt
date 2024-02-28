package com.example.curiosity.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.curiosity.ui.datasource.Module
import com.example.curiosity.ui.room.ModuleEvent
import com.example.curiosity.ui.room.ModuleState
import com.example.curiosity.ui.room.SortType
import kotlin.reflect.KFunction1

@Composable
fun ModuleScreen(
    state: ModuleState,
    onEvent: KFunction1<ModuleEvent, Unit>
)
{
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ModuleEvent.ShowDialog) })
            {
                Icon( imageVector = Icons.Default.Add, contentDescription = "Add module")
            }
        },
        modifier = Modifier.padding(16.dp)
    ){ innerPadding ->
        if( state.isAddingModule )
        {
            AddModuleDialog( state = state, onEvent =  onEvent )
        }

        if ( state.isShowSettings )
        {
            Log.d("Padááš?", "${state.moduleID}")
            DialogModuleSettings(
                state   = state,
                onEvent = onEvent,
                module = state.modules[ state.moduleID ]
            )
        }
        HomeBody(
            onModuleClick = {},
            state = state,
            onEvent = onEvent,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun HomeBody(
    onModuleClick: (Int) -> Unit,
    state: ModuleState,
    onEvent: KFunction1<ModuleEvent, Unit>,
    modifier: Modifier = Modifier
)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (state.modules.isEmpty()) {
            Text(
                text = "To add module click on the plus button.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            SortModuleList(
                state = state,
                onEvent = onEvent,
                modifier = Modifier
            )
            ModuleList(
                itemList = state.modules,
                onModuleClick = {
                    onModuleClick( it.id )
                                },
                onEvent = onEvent,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun SortModuleList(
    state: ModuleState,
    onEvent: KFunction1<ModuleEvent, Unit>,
    modifier: Modifier
)
{
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy( 16.dp )
    ) {
        item {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically
            ){
                SortType.entries.forEach { sortType ->
                    Row (
                        modifier = Modifier
                            .clickable {
                                onEvent(ModuleEvent.SortModules( sortType ))
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = state.sortType == sortType,
                            onClick = {
                                onEvent(ModuleEvent.SortModules(sortType))
                            })
                        Text(text = sortType.name)
                    }
                }
            }
        }
    }
}


@Composable
private fun ModuleList(
    itemList: List<Module>,
    onModuleClick: (Module) -> Unit,
    onEvent: KFunction1<ModuleEvent, Unit>,
    modifier: Modifier
)
{
    LazyColumn(modifier = modifier) {
        items(items = itemList, key = { it.id } ) { item ->
            ModuleCard(module = item,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onModuleClick(item)
                        onEvent(ModuleEvent.ShowSettingsDialog( itemList.indexOf(item) ))
                    })
        }
    }
}

@Composable
private fun ModuleCard(
    module: Module,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = module.moduleName,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = module.moduleType,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = module.topic,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}