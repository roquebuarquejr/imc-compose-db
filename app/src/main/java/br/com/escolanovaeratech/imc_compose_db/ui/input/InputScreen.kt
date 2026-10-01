package br.com.escolanovaeratech.imc_compose_db.ui.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.escolanovaeratech.imc_compose_db.R
import br.com.escolanovaeratech.imc_compose_db.data.BmiResult
import br.com.escolanovaeratech.imc_compose_db.domain.BmiCalculator
import br.com.escolanovaeratech.imc_compose_db.ui.components.HeightGlyph
import br.com.escolanovaeratech.imc_compose_db.ui.components.HistoryTextButton
import br.com.escolanovaeratech.imc_compose_db.ui.components.ImcHeader
import br.com.escolanovaeratech.imc_compose_db.ui.components.WeightGlyph
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcButton
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcFieldBackground
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcHint
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcScreenBackground
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImcText
import br.com.escolanovaeratech.imc_compose_db.ui.theme.ImccomposedbTheme

@Composable
fun InputScreen(
    onCalculate: (BmiResult) -> Unit,
    onOpenHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var showError by rememberSaveable { mutableStateOf(false) }
    val invalidMessage = stringResource(R.string.invalid_input)

    fun submit() {
        val weightKg = parsePositive(weight)
        val heightMeters = parsePositive(height)?.let(::heightInMeters)
        if (weightKg == null || heightMeters == null) {
            showError = true
            return
        }
        showError = false
        onCalculate(
            BmiResult(
                weightKg = weightKg,
                heightMeters = heightMeters,
                bmi = BmiCalculator.calculate(weightKg, heightMeters),
            ),
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ImcScreenBackground),
    ) {
        ImcHeader(description = stringResource(R.string.input_description))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 32.dp, bottom = 16.dp),
        ) {
            ImcTextField(
                value = weight,
                onValueChange = {
                    showError = false
                    weight = sanitizeDecimal(it)
                },
                placeholder = stringResource(R.string.weight_hint),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next,
                ),
                icon = { WeightGlyph() },
            )
            Spacer(Modifier.height(16.dp))
            ImcTextField(
                value = height,
                onValueChange = {
                    showError = false
                    height = sanitizeDecimal(it)
                },
                placeholder = stringResource(R.string.height_hint),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(onDone = { submit() }),
                icon = { HeightGlyph() },
            )
            if (showError) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = invalidMessage,
                    color = Color(0xFFD32F2F),
                    fontSize = 13.sp,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.ime.union(WindowInsets.navigationBars))
                .padding(horizontal = 24.dp)
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = ::submit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50),

            ) {
                Text(
                    text = stringResource(R.string.calculate),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            HistoryTextButton(onClick = onOpenHistory)
        }
    }
}

private fun sanitizeDecimal(raw: String): String {
    val builder = StringBuilder()
    var separatorUsed = false
    raw.forEach { char ->
        when {
            char.isDigit() -> builder.append(char)
            (char == '.' || char == ',') && !separatorUsed -> {
                separatorUsed = true
                builder.append('.')
            }
        }
    }
    return builder.toString()
}

private fun parsePositive(raw: String): Double? {
    val normalized = raw.trim().replace(',', '.')
    if (normalized.isEmpty() || normalized == ".") return null
    return normalized.toDoubleOrNull()?.takeIf { it > 0.0 }
}

private fun heightInMeters(value: Double): Double {
    return if (value > 3.0) value / 100.0 else value
}

@Composable
private fun ImcTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(ImcFieldBackground, RoundedCornerShape(14.dp)),
        singleLine = true,
        textStyle = TextStyle(
            color = ImcText,
            fontSize = 15.sp,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(ImcButton),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = ImcHint,
                            fontSize = 14.sp,
                            maxLines = 1,
                        )
                    }
                    innerTextField()
                }
                Spacer(Modifier.width(12.dp))
                icon()
            }
        },
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun InputScreenPreview() {
    ImccomposedbTheme(dynamicColor = false, darkTheme = false) {
        InputScreen(
            onCalculate = {},
            onOpenHistory = {},
        )
    }
}
