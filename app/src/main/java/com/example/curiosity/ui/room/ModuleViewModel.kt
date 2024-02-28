package com.example.curiosity.ui.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curiosity.ui.datasource.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ModuleViewModel(
    private val dao: ModuleDao
): ViewModel() {

    private val _sortType = MutableStateFlow( SortType.NAME )
    private val _modules = _sortType
        .flatMapLatest { sortType  ->
            when( sortType )
            {
                SortType.NAME -> dao.getModulesOrderedByName()
                SortType.TYPE -> dao.getModulesOrderedByType()
                SortType.ACTIVE_STATE -> dao.getModulesOrderedByActiveState()
            }
        }
        .stateIn( viewModelScope, SharingStarted.WhileSubscribed(), emptyList() )

    private val _state = MutableStateFlow( ModuleState() )
    val state = combine( _state, _sortType, _modules ) { state, sortType, modules ->
        state.copy(
            modules = modules,
            sortType = sortType
        )
    }.stateIn( viewModelScope, SharingStarted.WhileSubscribed( 5000 ), ModuleState() )

    fun onEvent( event: ModuleEvent )
    {
        when ( event )
        {
            is ModuleEvent.DeleteModule -> {
                viewModelScope.launch {
                    dao.deleteModule( event.module )
                }
            }
            ModuleEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingModule = false
                ) }
            }
            ModuleEvent.SaveModule -> {
                val moduleName = state.value.moduleName
                val moduleType = state.value.moduleType
                val topic      = state.value.topic

                if ( moduleName.isBlank() || moduleType.isBlank() || topic.isBlank() )
                {
                    Log.i( "ModuleEvent.SaveModule", "Did not proceed.")
                    return
                }

                val module = Module(
                    moduleName = moduleName,
                    moduleType = moduleType,
                    topic = topic,
                    alarmingInfo = "",
                    activeState = true,
                    lastDeviceState = ""
                )

                viewModelScope.launch {
                    dao.upsertModule(module)
                }

                _state.update { it.copy(
                    isAddingModule = false,
                    moduleName = "",
                    moduleType = "",
                    topic = "",
                    alarmingInfo = "",
                    activeState = true,
                    lastDeviceState = "",
                    isShowSettings = false
                ) }
            }
            is ModuleEvent.SetActiveState -> {
                    _state.update { it.copy(
                        activeState = !it.activeState
                    ) }
            }
            is ModuleEvent.SetAlarmingInfo -> {
                    _state.update { it.copy(
                        alarmingInfo = event.alarmInfo
                    ) }
            }
            is ModuleEvent.SetLastDeviceState -> {
                _state.update { it.copy(
                    lastDeviceState = event.lastDeviceState
                ) }
            }
            is ModuleEvent.SetModuleName -> {
                _state.update { it.copy(
                    moduleName = event.moduleName
                ) }
            }
            is ModuleEvent.SetModuleType -> {
                _state.update { it.copy(
                    moduleType = event.moduleType
                ) }
            }
            is ModuleEvent.SetTopic -> {
                _state.update { it.copy(
                    topic = event.topic
                ) }
            }
            ModuleEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingModule = true
                    )
                }
            }
            is ModuleEvent.SortModules -> {
                    _sortType.value = event.sortType
            }

            ModuleEvent.HideSettingDialog -> {
                _state.update { it.copy(
                    isShowSettings = false
                ) }
            }
            is ModuleEvent.ShowSettingsDialog -> {
                _state.update { it.copy(
                    moduleName = state.value.modules[event.moduleID].moduleName,
                    moduleType = state.value.modules[event.moduleID].moduleType,
                    topic = state.value.modules[event.moduleID].topic,
                    isShowSettings = true
                ) }
            }

            ModuleEvent.HideTypeDialog -> {
                _state.update { it.copy(
                    isShowTypeDialog = false
                )}
            }
            ModuleEvent.ShowTypeDialog -> {
                _state.update { it.copy(
                    isShowTypeDialog = true
                ) }
            }

            ModuleEvent.UpdateModule -> {
                val module = Module(
                    moduleName = state.value.moduleName,
                    moduleType = state.value.moduleType,
                    topic = state.value.topic,
                    alarmingInfo = "alarmingInfo",
                    activeState = true,
                    lastDeviceState = "",
                    id = 1
                )
                viewModelScope.launch {
                    dao.upsertModule(module)
                }

                _state.update { it.copy(
                    isAddingModule = false,
                    moduleName = "",
                    moduleType = "",
                    topic = "",
                    alarmingInfo = "",
                    activeState = true,
                    lastDeviceState = "",
                    isShowSettings = false,
                    moduleID = state.value.moduleID
                ) }

            }
        }
    }
}
