package br.com.escolanovaeratech.imc_compose_db.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.escolanovaeratech.imc_compose_db.R
import br.com.escolanovaeratech.imc_compose_db.data.local.BmiRecord
import br.com.escolanovaeratech.imc_compose_db.domain.BmiCalculator
import br.com.escolanovaeratech.imc_compose_db.ui.components.ImcHeader
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcClassification
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcFieldBackground
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcLabel
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcScreenBackground
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcText
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImccomposedbTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    records: List<BmiRecord>,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ImcScreenBackground),
    ) {
        ImcHeader(description = stringResource(R.string.history_description))

        if (records.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .navigationBarsPadding(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.history_empty),
                    color = ImcLabel,
                    fontSize = 15.sp,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .navigationBarsPadding(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 12.dp),
            ) {
                items(records, key = { it.id }) { record ->
                    HistoryRow(record, onDelete = { onDelete(record.id) })
                }
            }
        }
    }
}

@Composable
private fun HistoryRow(
    record: BmiRecord,
    onDelete: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp),
                ) {
                    Text(
                        text = BmiCalculator.format(record.bmi),
                        color = ImcText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = record.classification,
                        color = ImcClassification,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = stringResource(
                            R.string.history_details,
                            formatMeasure(record.weightKg, decimals = 1),
                            formatMeasure(record.heightMeters, decimals = 2),
                        ),
                        color = ImcLabel,
                        fontSize = 13.sp,
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = formatCalculatedAt(record.calculatedAt),
                        color = ImcLabel,
                        fontSize = 12.sp,
                    )
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = stringResource(R.string.history_delete),
                        tint = ImcLabel,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(24.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onDelete,
                            ),
                    )
                }
            }
        }
        HorizontalDivider(color = ImcFieldBackground, thickness = 1.dp)
    }
}

private fun formatCalculatedAt(epochMillis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("pt-BR"))
    return formatter.format(Date(epochMillis))
}

private fun formatMeasure(value: Double, decimals: Int): String {
    return String.format(Locale.US, "%.${decimals}f", value)
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun HistoryScreenPreview() {
    ImccomposedbTheme(dynamicColor = false, darkTheme = false) {
        HistoryScreen(
            records = listOf(
                BmiRecord(
                    id = 1,
                    weightKg = 70.2,
                    heightMeters = 1.72,
                    bmi = 23.66,
                    classification = "NORMAL",
                    calculatedAt = 1_759_300_000_000,
                ),
            ),
            onDelete = {},
        )
    }
}
