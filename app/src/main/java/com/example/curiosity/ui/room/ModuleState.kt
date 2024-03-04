package com.example.curiosity.ui.room

import com.example.curiosity.ui.datasource.Module

data class ModuleState (
    var modules: List<Module> = emptyList(),
    var moduleName: String = "",
    var moduleType: String = "",
    var topic: String = "",
    var alarmingInfo: String = "",
    var activeState: Boolean = false,
    var lastDeviceState: String = "",
    var isAddingModule: Boolean = false,
    var isShowSettings: Boolean = false,
    var isShowTypeDialog: Boolean = false,
    var isTimePicker: Boolean = false,
    var moduleID: Int = 0,
    var listIndex: Int = 0,
    var sortType: SortType = SortType.NAME
)


