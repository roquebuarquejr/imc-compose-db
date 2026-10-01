package br.com.escolanovaeratech.imc_compose_db.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.escolanovaeratech.imc_compose_db.R
import br.com.escolanovaeratech.imc_compose_db.ui.components.ImcLogo
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcGradient
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImccomposedbTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        delay(SPLASH_DURATION_MS.milliseconds)
        onFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ImcGradient),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImcLogo(size = 112.dp)
            Spacer(Modifier.height(28.dp))
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.splash_subtitle),
                color = Color.White.copy(alpha = 0.92f),
                fontSize = 16.sp,
            )
        }

        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 56.dp)
                .size(32.dp),
            color = Color.White,
            strokeWidth = 3.dp,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round,
        )
    }
}

private const val SPLASH_DURATION_MS = 1_800L

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun SplashScreenPreview() {
    ImccomposedbTheme(dynamicColor = false, darkTheme = false) {
        SplashScreen(onFinished = {})
    }
}
