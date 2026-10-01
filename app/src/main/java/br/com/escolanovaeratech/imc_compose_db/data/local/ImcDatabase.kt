package br.com.escolanovaeratech.imc_compose_db.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BmiRecord::class],
    version = 1,
    exportSchema = false,
)
abstract class ImcDatabase : RoomDatabase() {
    abstract fun bmiDao(): BmiDao

    companion object {
        @Volatile
        private var instance: ImcDatabase? = null

        fun getInstance(context: Context): ImcDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ImcDatabase::class.java,
                    "imc.db",
                ).build().also { instance = it }
            }
        }
    }
}
