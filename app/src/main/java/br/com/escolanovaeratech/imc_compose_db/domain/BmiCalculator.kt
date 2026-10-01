package br.com.escolanovaeratech.imc_compose_db.domain

import java.util.Locale

object BmiCalculator {
    fun calculate(weightKg: Double, heightMeters: Double): Double {
        return weightKg / (heightMeters * heightMeters)
    }

    fun format(bmi: Double): String = String.format(Locale.US, "%.2f", bmi)

    fun classify(bmi: Double): String = when {
        bmi < 18.5 -> "ABAIXO DO PESO"
        bmi < 25.0 -> "NORMAL"
        bmi < 30.0 -> "SOBREPESO"
        bmi < 35.0 -> "OBESIDADE GRAU I"
        bmi < 40.0 -> "OBESIDADE GRAU II"
        else -> "OBESIDADE GRAU III"
    }
}
