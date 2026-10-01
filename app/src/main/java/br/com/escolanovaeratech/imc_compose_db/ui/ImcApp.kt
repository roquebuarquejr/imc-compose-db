package br.com.escolanovaeratech.imc_compose_db.ui

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.escolanovaeratech.imc_compose_db.data.local.BmiRecord
import br.com.escolanovaeratech.imc_compose_db.data.local.ImcDatabase
import br.com.escolanovaeratech.imc_compose_db.domain.BmiCalculator
import br.com.escolanovaeratech.imc_compose_db.ui.history.HistoryScreen
import br.com.escolanovaeratech.imc_compose_db.ui.input.InputScreen
import br.com.escolanovaeratech.imc_compose_db.ui.navigation.ImcRoutes
import br.com.escolanovaeratech.imc_compose_db.ui.result.ResultScreen
import br.com.escolanovaeratech.imc_compose_db.ui.splash.SplashScreen
import kotlinx.coroutines.launch

@Composable
fun ImcApp() {
    val context = LocalContext.current
    val dao = remember { ImcDatabase.getInstance(context).bmiDao() }
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val isSplash = currentRoute == null || currentRoute == ImcRoutes.Splash

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = false
            controller.isAppearanceLightNavigationBars = !isSplash
        }
    }

    NavHost(
        navController = navController,
        startDestination = ImcRoutes.Splash,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(ImcRoutes.Splash) {
            SplashScreen(
                onFinished = {
                    navController.navigate(ImcRoutes.Input) {
                        popUpTo(ImcRoutes.Splash) { inclusive = true }
                    }
                },
            )
        }

        composable(ImcRoutes.Input) {
            InputScreen(
                onCalculate = { result ->
                    scope.launch {
                        dao.insert(
                            BmiRecord(
                                weightKg = result.weightKg,
                                heightMeters = result.heightMeters,
                                bmi = result.bmi,
                                classification = BmiCalculator.classify(result.bmi),
                                calculatedAt = System.currentTimeMillis(),
                            ),
                        )
                    }
                    navController.navigate(ImcRoutes.result(result.bmi))
                },
                onOpenHistory = { navController.navigate(ImcRoutes.History) },
            )
        }

        composable(
            route = ImcRoutes.Result,
            arguments = listOf(
                navArgument(ImcRoutes.BmiArg) { type = NavType.StringType },
            ),
        ) { entry ->
            val bmi = entry.arguments?.getString(ImcRoutes.BmiArg)?.toDoubleOrNull() ?: 0.0
            ResultScreen(
                bmi = bmi,
                onOpenHistory = { navController.navigate(ImcRoutes.History) },
            )
        }

        composable(ImcRoutes.History) {
            var records by remember { mutableStateOf(emptyList<BmiRecord>()) }
            LaunchedEffect(Unit) {
                records = dao.getAll()
            }
            HistoryScreen(
                records = records,
                onDelete = { id ->
                    scope.launch {
                        dao.deleteById(id)
                        records = dao.getAll()
                    }
                },
            )
        }
    }
}
