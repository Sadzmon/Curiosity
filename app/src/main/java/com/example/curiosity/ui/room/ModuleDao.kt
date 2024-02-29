package com.example.curiosity.ui.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.curiosity.ui.datasource.Module
import kotlinx.coroutines.flow.Flow

@Dao
interface ModuleDao {

    /**
     * Create new module or update with existing ID.
     */
    @Upsert
    suspend fun upsertModule( module: Module )

    /**
     * Delete module.
     */
    @Delete
    suspend fun deleteModule( module: Module )

    @Query("SELECT * FROM module ORDER BY moduleName ASC")
    fun getModulesOrderedByName(): Flow<List<Module>>

    @Query("SELECT * FROM module ORDER BY moduleType ASC")
    fun getModulesOrderedByType(): Flow<List<Module>>

    @Query("SELECT * FROM module ORDER BY activeState ASC")
    fun getModulesOrderedByActiveState(): Flow<List<Module>>

    @Query("SELECT * from module WHERE id = :id")
    fun getModuleByID( id: Int ): Flow<Module>
}