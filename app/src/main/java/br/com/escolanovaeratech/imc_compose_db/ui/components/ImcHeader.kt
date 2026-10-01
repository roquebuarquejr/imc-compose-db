package br.com.escolanovaeratech.imc_compose_db.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.escolanovaeratech.imc_compose_db.R
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcGradient

@Composable
fun ImcHeader(
    description: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = ImcGradient,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
            )
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(top = 12.dp, bottom = 28.dp),
    ) {
        ImcLogo(size = 36.dp)
        Spacer(Modifier.height(18.dp))
        Text(
            text = stringResource(R.string.app_name),
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = description,
            color = Color.White,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        )
        content()
    }
}
