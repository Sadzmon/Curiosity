package com.example.curiosity.ui.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Module(
    var moduleName: String,
    var moduleType: String,
    var topic: String,
    var alarmingInfo: String,
    var activeState: Boolean,
    var lastDeviceState: String,
    @PrimaryKey( autoGenerate = true )
    var id: Int = 0
)
