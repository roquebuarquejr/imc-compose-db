package br.com.escolanovaeratech.imc_compose_db.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmi_calculations")
data class BmiRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val weightKg: Double,
    val heightMeters: Double,
    val bmi: Double,
    val classification: String,
    val calculatedAt: Long,
)
