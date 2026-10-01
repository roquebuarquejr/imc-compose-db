package br.com.escolanovaeratech.imc_compose_db.ui.navigation

import br.com.escolanovaeratech.imc_compose_db.domain.BmiCalculator

object ImcRoutes {
    const val Splash = "splash"
    const val Input = "input"
    const val History = "history"
    const val Result = "result?bmi={bmi}"
    const val BmiArg = "bmi"

    fun result(bmi: Double): String = "result?bmi=${BmiCalculator.format(bmi)}"
}
