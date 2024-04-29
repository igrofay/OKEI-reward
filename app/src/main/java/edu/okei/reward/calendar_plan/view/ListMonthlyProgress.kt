package edu.okei.reward.calendar_plan.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import edu.okei.reward.R
import edu.okei.reward.calendar_plan.model.CalendarPlanState
import edu.okei.reward.common.ui.theme.dimensions

@Composable
fun ListMonthlyProgress(
    state: CalendarPlanState.MonthProgress
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement
            .spacedBy(MaterialTheme.dimensions.grid_4_5),
        contentPadding = PaddingValues(
            vertical = MaterialTheme.dimensions.grid_5_5,
            horizontal = MaterialTheme.dimensions.grid_4
        )
    ) {
        item {
            MonthReportItem(state.model.currentMonth)
        }
        item {
            MonthReportItem(state.model.previousMonth)
        }
    }
}

@Composable
fun MonthReportItem(
    monthReport: MonthlyProgressModel.MonthReportModel
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
                vertical = MaterialTheme.dimensions.grid_5_5 * 2,
                horizontal = MaterialTheme.dimensions.grid_5 * 1.5f
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .border(
                        MaterialTheme.dimensions.borderSmall,
                        MaterialTheme.colors.primary,
                        MaterialTheme.shapes.medium
                    )
                    .padding(MaterialTheme.dimensions.grid_2_5),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = if (monthReport.underWay)
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
            Column {
                Text(
                    text = monthReport.name,
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
                val date = monthReport.lastChange ?: stringResource(R.string.no)
                Text(
                    text = "${stringResource(R.string.last_change)}: $date",
                    style = MaterialTheme.typography.caption.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .scale(MaterialTheme.dimensions.coefficient)
                    .size(48.dp)
            )
        }
    }
}