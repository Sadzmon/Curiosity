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

    /**
     * Convert ModuleState to Module.
     */
    private fun ModuleState.toModule(): Module = Module(
        moduleName = moduleName,
        moduleType = moduleType,
        topic = topic,
        alarmingInfo = alarmingInfo,
        activeState = activeState,
        lastDeviceState = lastDeviceState,
        id = moduleID
    )

    private val _sortType = MutableStateFlow( SortType.NAME )
    private val _modules = _sortType
        .flatMapLatest { sortType  ->
            when( sortType )
            {
                SortType.NAME -> dao.getModulesOrderedByName()
                SortType.TYPE -> dao.getModulesOrderedByType()
                SortType.ACTIVE -> dao.getModulesOrderedByActiveState()
            }
        }
        .stateIn( viewModelScope, SharingStarted.WhileSubscribed(), emptyList() )

    private val _state = MutableStateFlow( ModuleState() )

    /**
     * Set _state default values.
     */
    private fun _stateClear()
    {
        _state.update { it.copy(
            isAddingModule = false,
            moduleName = "",
            moduleType = "",
            topic = "",
            alarmingInfo = "",
            activeState = true,
            lastDeviceState = "",
            isShowSettings = false,
            moduleID = 0
        ) }
    }

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

                viewModelScope.launch {
                    dao.upsertModule( state.value.toModule() )
                }
                _stateClear()
            }
            is ModuleEvent.SetActiveState -> {
                    _state.update { it.copy(
                        activeState = !it.activeState
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
                        moduleID = 0,
                        isAddingModule = true
                    )
                }
            }
            is ModuleEvent.SortModules -> {
                    _sortType.value = event.sortType
            }

            ModuleEvent.HideSettingDialog -> {
                _stateClear()
            }
            is ModuleEvent.ShowSettingsDialog -> {
                _state.update { it.copy(
                    listIndex  = event.listIndex,
                    moduleID   = state.value.modules[event.listIndex].id,
                    moduleName = state.value.modules[event.listIndex].moduleName,
                    moduleType = state.value.modules[event.listIndex].moduleType,
                    topic      = state.value.modules[event.listIndex].topic,
                    isShowSettings = true
                ) }
                Log.w("id", "${state.value.moduleID}")
                Log.e("indexId", "")
            }

            ModuleEvent.HideTypeDialog -> {
                _state.update { it.copy(
                    moduleType = state.value.modules[ state.value.listIndex ].moduleType,
                    isShowTypeDialog = false
                )}
            }
            ModuleEvent.ShowTypeDialog -> {
                _state.update { it.copy(
                    isShowTypeDialog = true
                ) }
            }

            is ModuleEvent.SaveTypeDialog -> {
                _state.update { it.copy(
                    moduleType = event.moduleType,
                    isShowTypeDialog = false
                ) }
            }

            ModuleEvent.HideTimepicker -> {
                _state.update { it.copy(
                    isTimePicker = false
                ) }
            }
            is ModuleEvent.ShowTimepicker -> {
                _state.update { it.copy(
                    alarmingInfo = state.value.modules[event.listIndex].alarmingInfo,
                    isTimePicker = true
                ) }
            }
            is ModuleEvent.SetAlarmingInfo -> {
                state.value.modules[state.value.listIndex].alarmingInfo = event.alarmInfo
                _state.update { it.copy(
                    isTimePicker = false
                ) }
            }
        }
    }
}
