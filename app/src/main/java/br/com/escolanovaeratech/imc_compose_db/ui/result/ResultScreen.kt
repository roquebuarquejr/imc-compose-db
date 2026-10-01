package br.com.escolanovaeratech.imc_compose_db.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.escolanovaeratech.imc_compose_db.R
import br.com.escolanovaeratech.imc_compose_db.domain.BmiCalculator
import br.com.escolanovaeratech.imc_compose_db.ui.components.HistoryTextButton
import br.com.escolanovaeratech.imc_compose_db.ui.components.ImcHeader
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcClassification
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcLabel
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcScreenBackground
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImccomposedbTheme

@Composable
fun ResultScreen(
    bmi: Double,
    onOpenHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ImcScreenBackground),
    ) {
        ImcHeader(
            description = stringResource(R.string.result_description),
            modifier = Modifier.weight(1f),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = BmiCalculator.format(bmi),
                    color = Color.White,
                    fontSize = 68.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .navigationBarsPadding(),
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.classification_label),
                    color = ImcLabel,
                    fontSize = 14.sp,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = BmiCalculator.classify(bmi),
                    color = ImcClassification,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.6.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }
            HistoryTextButton(
                onClick = onOpenHistory,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ResultScreenPreview() {
    ImccomposedbTheme(dynamicColor = false, darkTheme = false) {
        ResultScreen(bmi = 23.66, onOpenHistory = {})
    }
}
