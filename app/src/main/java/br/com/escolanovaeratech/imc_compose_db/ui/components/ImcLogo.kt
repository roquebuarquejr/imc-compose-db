package br.com.escolanovaeratech.imc_compose_db.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.outlined.Height
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcIcon

@Composable
fun ImcLogo(
    modifier: Modifier = Modifier,
    size: Dp = 104.dp,
    color: Color = Color.White,
) {
    Box(
        modifier = modifier
            .size(size)
            .border(
                width = size * 0.055f,
                color = color,
                shape = RoundedCornerShape(size * 0.18f),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Scale,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(size * 0.55f),
        )
    }
}

@Composable
fun WeightGlyph(
    modifier: Modifier = Modifier,
    tint: Color = ImcIcon,
) {
    Icon(
        imageVector = Icons.Outlined.MonitorWeight,
        contentDescription = null,
        tint = tint,
        modifier = modifier.size(22.dp),
    )
}

@Composable
fun HeightGlyph(
    modifier: Modifier = Modifier,
    tint: Color = ImcIcon,
) {
    Icon(
        imageVector = Icons.Outlined.Height,
        contentDescription = null,
        tint = tint,
        modifier = modifier.size(22.dp),
    )
}
