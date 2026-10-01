package br.com.escolanovaeratech.imc_compose_db

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.escolanovaeratech.imc_compose_db.ui.ImcApp
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImccomposedbTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        setContent {
            ImccomposedbTheme(dynamicColor = false, darkTheme = false) {
                ImcApp()
            }
        }
    }
}
