package br.com.escolanovaeratech.imc_compose_db.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.escolanovaeratech.imc_compose_db.R
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcClassification

@Composable
fun HistoryTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(
            text = stringResource(R.string.history),
            color = ImcClassification,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}
