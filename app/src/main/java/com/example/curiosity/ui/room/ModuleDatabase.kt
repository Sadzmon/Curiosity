package com.example.curiosity.ui.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.curiosity.ui.datasource.Module

@Database(
    entities = [Module::class],
    version = 1
)
abstract class ModuleDatabase: RoomDatabase() {

    abstract val dao: ModuleDao
}