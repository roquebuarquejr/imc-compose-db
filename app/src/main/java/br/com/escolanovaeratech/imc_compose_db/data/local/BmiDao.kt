package br.com.escolanovaeratech.imc_compose_db.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BmiDao {
    @Query("SELECT * FROM bmi_calculations ORDER BY calculatedAt DESC")
    suspend fun getAll(): List<BmiRecord>

    @Insert
    suspend fun insert(record: BmiRecord)

    @Query("DELETE FROM bmi_calculations WHERE id = :id")
    suspend fun deleteById(id: Long)
}
