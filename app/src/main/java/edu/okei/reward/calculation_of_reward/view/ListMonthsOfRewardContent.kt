package edu.okei.reward.calculation_of_reward.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import edu.okei.reward.R
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardEvent
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardState
import edu.okei.reward.common.ui.button.CustomOutlinedButton
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.EventBase

@Composable
fun ListMonthsOfRewardContent(
    state: CalculationOfRewardState.ListMonthsOfReward,
    event: EventBase<CalculationOfRewardEvent>,
    lazyListState: LazyListState,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement
            .spacedBy(MaterialTheme.dimensions.grid_4_5),
        contentPadding = PaddingValues(
            start = MaterialTheme.dimensions.grid_4,
            end = MaterialTheme.dimensions.grid_4,
            top = MaterialTheme.dimensions.grid_5_5,
            bottom = MaterialTheme.dimensions.grid_5_5
        ),
        state = lazyListState
    ) {
        item {
            MonthRewardItem(model = state.monthlyProgress.currentMonth) {
                event.onEvent(CalculationOfRewardEvent.SeeReportForMonth(state.monthlyProgress.currentMonth.monthIndex))
            }
        }
        item {
            MonthRewardItem(model = state.monthlyProgress.previousMonth) {
                event.onEvent(CalculationOfRewardEvent.SeeReportForMonth(state.monthlyProgress.previousMonth.monthIndex))
            }
        }
    }
}

@Composable
private fun MonthRewardItem(
    model: MonthlyProgressModel.MonthReportModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                MaterialTheme.dimensions.borderSmall,
                MaterialTheme.colors.primary,
                MaterialTheme.shapes.medium
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimensions.grid_5_5 * 2,
                )
                .padding(start = MaterialTheme.dimensions.grid_5 * 1.5f)
                .border(
                    MaterialTheme.dimensions.borderSmall,
                    MaterialTheme.colors.primary,
                    MaterialTheme.shapes.medium
                )
                .padding(MaterialTheme.dimensions.grid_2_5),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = if (model.underWay)
                    painterResource(R.drawable.ic_clock)
                else
                    painterResource(R.drawable.ic_check),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .scale(MaterialTheme.dimensions.coefficient)
                    .size(34.dp)
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.dimensions.grid_5))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = model.name,
                style = MaterialTheme.typography.h5.copy(
                    color = MaterialTheme.colors.primary
                )
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.dimensions.grid_2))
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(MaterialTheme.dimensions.borderSmall)
                .background(MaterialTheme.colors.primary)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = MaterialTheme.dimensions.grid_3_5),
            contentAlignment = Alignment.Center
        ) {
            CustomOutlinedButton(
                label = if (model.underWay)
                    stringResource(R.string.calculate)
                else
                    stringResource(R.string.report),
                onClick = onClick
            )
        }
    }
}