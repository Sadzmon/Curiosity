package com.example.curiosity.ui.room

import com.example.curiosity.ui.datasource.Module

/*
    Sealed interface predefine set of classes known for compiler, so after compilation there no
    new class can inherit from parent. In some sense similar to ENUM.
 */

sealed interface ModuleEvent {
    data object SaveModule: ModuleEvent
    data class SetModuleName( val moduleName: String ): ModuleEvent
    data class SetModuleType( val moduleType: String ): ModuleEvent
    data class SetTopic( val topic: String ): ModuleEvent
    data class SetActiveState( val activeState: Boolean ): ModuleEvent
    data class SetLastDeviceState( val lastDeviceState: String ): ModuleEvent
    data object ShowDialog: ModuleEvent
    data object HideDialog: ModuleEvent
    data class ShowSettingsDialog( val listIndex: Int ): ModuleEvent
    data object HideSettingDialog: ModuleEvent
    data object ShowTypeDialog: ModuleEvent
    data object HideTypeDialog: ModuleEvent
    data class SaveTypeDialog( val moduleType: String ): ModuleEvent
    data class ShowTimepicker( val listIndex: Int ): ModuleEvent
    data object HideTimepicker: ModuleEvent
    data class SetAlarmingInfo( val alarmInfo: String ): ModuleEvent
    data class SortModules(val sortType: SortType): ModuleEvent
    data class DeleteModule( val module: Module): ModuleEvent
}