package edu.okei.reward.calculation_of_reward.view


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.okei.core.domain.model.statistics.CreateReportDataModel
import edu.okei.core.domain.model.statistics.TeachersRewardReportModel
import edu.okei.reward.R
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardEvent
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardState
import edu.okei.reward.calculation_of_reward.model.SuffixVisualTransformation
import edu.okei.reward.common.ui.button.CustomOutlinedButton
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.EventBase

@Composable
fun ReportDataFieldsContent(
    reportDataModel: TeachersRewardReportModel.ReportDataModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f),
        contentPadding = PaddingValues(
            top = MaterialTheme.dimensions.grid_5_5,
            start = MaterialTheme.dimensions.grid_4,
            end = MaterialTheme.dimensions.grid_4,
            bottom = (WindowInsets.navigationBars
                .asPaddingValues().calculateBottomPadding() +
                    MaterialTheme.dimensions.grid_5_5)
        ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_4_5),
    ) {
        item {
            InputField(
                label = "Общая сумма премий",
                icon = painterResource(R.drawable.ic_piggy_bank),
                value = reportDataModel.totalAmountPremium.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            InputField(
                label = "Часть полугодовой премии",
                icon = painterResource(R.drawable.ic_calendar),
                value = reportDataModel.partSemiannualPremium.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            InputField(
                label = "Фиксированная премия",
                icon = painterResource(R.drawable.ic_workspace_premium),
                value = reportDataModel.fixedPremium.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            InputField(
                label = "Общее количество баллов",
                icon = painterResource(R.drawable.ic_timeline),
                value = reportDataModel.totalAmountPoints.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" балл.")
            )
        }
        item {
            InputField(
                label = "Распределяемая премия по баллам",
                icon = painterResource(R.drawable.ic_generating_tokens),
                value = reportDataModel.distributablePremium.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            InputField(
                label = "Стоимость балла",
                icon = painterResource(R.drawable.ic_currency_ruble),
                value = reportDataModel.costByPoint.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
    }
}

@Composable
fun ReportDataInputFieldsContent(
    state: CalculationOfRewardState.CreateReportForMonth,
    eventBase: EventBase<CalculationOfRewardEvent>,
    listLazyListState: LazyListState
) {
    LazyColumn(
        modifier = Modifier,
        state = listLazyListState,
        contentPadding = PaddingValues(
            top = MaterialTheme.dimensions.grid_5_5,
            start = MaterialTheme.dimensions.grid_4,
            end = MaterialTheme.dimensions.grid_4,
            bottom = (WindowInsets.navigationBars
                .asPaddingValues().calculateBottomPadding() +
                    MaterialTheme.dimensions.grid_5_5)
        ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_4_5),
    ) {
        item {
            InputField(
                label = "Общая сумма премий",
                icon = painterResource(R.drawable.ic_piggy_bank),
                value = state.totalAmountPremium.toString(),
                onValueChange = {
                    eventBase.onEvent(CalculationOfRewardEvent.InputTotalAmountPremium(it))
                },
                readOnly = false,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            InputField(
                label = "Часть полугодовой премии",
                icon = painterResource(R.drawable.ic_calendar),
                value = state.partSemiannualPremium.toString(),
                onValueChange = {
                    eventBase.onEvent(CalculationOfRewardEvent.InputPartSemiannualPremium(it))
                },
                readOnly = false,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            InputField(
                label = "Фиксированная премия",
                icon = painterResource(R.drawable.ic_workspace_premium),
                value = state.fixedPremium.toString(),
                onValueChange = {
                    eventBase.onEvent(CalculationOfRewardEvent.InputFixedPremium(it))
                },
                readOnly = false,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimensions.grid_5_5),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_2)
            ){
                Spacer(
                    modifier = Modifier
                        .height(MaterialTheme.dimensions.borderSmall)
                        .weight(1f)
                        .background(MaterialTheme.colors.primary)
                )
                Text(
                    text = "Результат",
                    style = MaterialTheme.typography.subtitle1
                        .copy(MaterialTheme.colors.primary)
                )
                Spacer(
                    modifier = Modifier
                        .height(MaterialTheme.dimensions.borderSmall)
                        .weight(1f)
                        .background(MaterialTheme.colors.primary)
                )
            }
        }
        item {
            InputField(
                label = "Общая фиксированная премия",
                icon = painterResource(R.drawable.ic_workspace_premium),
                value = state.fixedPremiumForTeachers.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            InputField(
                label = "Общее количество баллов",
                icon = painterResource(R.drawable.ic_timeline),
                value = state.totalAmountPoints.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" балл.")
            )
        }
        item {
            InputField(
                label = "Распределяемая премия по баллам",
                icon = painterResource(R.drawable.ic_generating_tokens),
                value = state.distributablePremium.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            InputField(
                label = "Стоимость балла",
                icon = painterResource(R.drawable.ic_currency_ruble),
                value = state.costByPoint.toString(),
                onValueChange = {},
                readOnly = true,
                visualTransformation = SuffixVisualTransformation(" руб.")
            )
        }
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_3_5))
        }
        item {
            CustomOutlinedButton(
                label = stringResource(R.string.calculate),
                style = MaterialTheme.typography.button
                    .copy(MaterialTheme.colors.primary),
                paddingValues = PaddingValues(
                    vertical = MaterialTheme.dimensions.grid_4_5
                )
            ) {
                eventBase.onEvent(CalculationOfRewardEvent.CreateReportData)
            }
        }
    }
}


@Composable
private fun InputField(
    label: String,
    icon: Painter,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean,
    visualTransformation: SuffixVisualTransformation,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                MaterialTheme.dimensions.borderSmall,
                MaterialTheme.colors.primary,
                MaterialTheme.shapes.medium
            )
            .padding(
                vertical = MaterialTheme.dimensions.grid_4_5,
                horizontal = MaterialTheme.dimensions.grid_5_5 * 2.5f
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.grid_2_5)
                .fillMaxWidth()
                .height(MaterialTheme.dimensions.borderSmall)
                .background(MaterialTheme.colors.primary.copy(0.5f))
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimensions.grid_2_5,
                )
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier
                    .scale(MaterialTheme.dimensions.coefficient)
                    .size(24.dp),
                tint = MaterialTheme.colors.primary
            )
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                visualTransformation = visualTransformation,
                readOnly = readOnly,
                singleLine = true,
                textStyle = MaterialTheme.typography.body1
                    .copy(
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    ),
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (!readOnly)
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier
                        .scale(MaterialTheme.dimensions.coefficient)
                        .size(24.dp),
                    tint = MaterialTheme.colors.primary
                )
            else
                Spacer(
                    modifier = Modifier
                        .scale(MaterialTheme.dimensions.coefficient)
                        .size(24.dp),
                )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimensions.borderSmall)
                .background(MaterialTheme.colors.primary.copy(0.5f))
        )
    }
}
