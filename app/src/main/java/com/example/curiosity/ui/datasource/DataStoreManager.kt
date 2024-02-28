import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * Class for persistent saving of data.
 */
class DataStoreManager( private val context: Context ) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("module_info")
        val ALARM_COUNT = intPreferencesKey("CA")
        val DOORBELL_COUNT = intPreferencesKey("CD")
        val FRIDGE_COUNT = intPreferencesKey("FC")
        val KETTLE_COUNT = intPreferencesKey("CK")
    }

    /**
     * Read data from key ALARM_COUNT
     */
    @Composable
    fun readAlarmCount(): Int
    {
        val key: Flow<Int?> = context.dataStore.data
            .map { preferences ->
                preferences[ ALARM_COUNT ] ?: 0
            }
        val data = key.collectAsState(initial = 0).value!!

        Log.w("Getting: ", "$data")
        return data
    }

    /**
     * Write data to key ALARM_COUNT
     */
    suspend fun writeAlarmCount( count: Int ) {
        context.dataStore.edit { module_info ->
            val data = module_info[ ALARM_COUNT ] ?: 0
            module_info[ ALARM_COUNT ] = count
        }
    }

    /**
     * Read data from key DOORBELL_COUNT
     */
    @Composable
    fun readDoorbellCount(): Int
    {
        val key: Flow<Int?> = context.dataStore.data
            .map { preferences ->
                preferences[ DOORBELL_COUNT ] ?: 0
            }
        val data = key.collectAsState( initial = 0 ).value!!

        Log.w("Getting: ", "$data")
        return data
    }

    /**
     * Write data to key DOORBELL_COUNT
     */
    suspend fun writeDoorbellCount( count: Int ) {
        context.dataStore.edit { module_info ->
            val data = module_info[ DOORBELL_COUNT ] ?: 0
            module_info[ DOORBELL_COUNT ] = count
        }
    }    /**
     * Read data from key FRIDGE_COUNT
     */
    @Composable
    fun readFridgeCount(): Int
    {
        val key: Flow<Int?> = context.dataStore.data
            .map { preferences ->
                preferences[ FRIDGE_COUNT ] ?: 0
            }
        val data = key.collectAsState(initial = 0).value!!

        Log.w("Getting: ", "$data")
        return data
    }

    /**
     * Write data to key FRIDGE_COUNT
     */
    suspend fun writeFridgeCount( count: Int ) {
        context.dataStore.edit { module_info ->
            val data = module_info[ FRIDGE_COUNT ] ?: 0
            module_info[ FRIDGE_COUNT ] = count
        }
    }    /**
     * Read data from key KETTLE_COUNT
     */
    @Composable
    fun readKettleCount(): Int
    {
        val key: Flow<Int?> = context.dataStore.data
            .map { preferences ->
                preferences[ KETTLE_COUNT ] ?: 0
            }
        val data = key.collectAsState(initial = 0).value!!

        Log.w("Getting: ", "$data")
        return data
    }

    /**
     * Write data to key KETTLE_COUNT
     */
    suspend fun writeKettleCount( count: Int ) {
        context.dataStore.edit { module_info ->
            val data = module_info[ KETTLE_COUNT ] ?: 0
            module_info[ KETTLE_COUNT ] = count
        }
    }

}
