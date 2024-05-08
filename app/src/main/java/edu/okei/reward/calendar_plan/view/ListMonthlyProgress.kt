package edu.okei.reward.calendar_plan.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import edu.okei.reward.R
import edu.okei.reward.calendar_plan.model.CalendarPlanEvent
import edu.okei.reward.calendar_plan.model.CalendarPlanState
import edu.okei.reward.common.ui.click.alphaClick
import edu.okei.reward.common.ui.load.CircularProgressBar
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.EventBase

@Composable
fun ListMonthlyProgress(
    state: CalendarPlanState.MonthProgress,
    eventBase: EventBase<CalendarPlanEvent>
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
            bottom = MaterialTheme.dimensions.grid_5_5 * 8f
        )
    ) {
        item {
            MonthReportItem(state.model.currentMonth){
                eventBase.onEvent(
                    CalendarPlanEvent.SeeTeachersInMonth(
                        state.model.currentMonth.monthIndex
                    )
                )
            }
        }
        item {
            MonthReportItem(state.model.previousMonth){
                eventBase.onEvent(
                    CalendarPlanEvent.SeeTeachersInMonth(
                        state.model.previousMonth.monthIndex
                    )
                )
            }
        }
    }
}


@Composable
fun MonthReportItem(
    monthReport: MonthlyProgressModel.MonthReportModel,
    onClick: ()->Unit
) {
    var isVisibleTopTeacherAndProgress by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .alphaClick { onClick() }
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
            .animateContentSize(
                animationSpec = tween(
                    easing = LinearOutSlowInEasing
                )
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
            Column(
                modifier = Modifier
                    .weight(1f)
            ){
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
            Spacer(modifier = Modifier.width(MaterialTheme.dimensions.grid_2))
            val rotateIcon by animateFloatAsState(
                if (isVisibleTopTeacherAndProgress) 180f else 0f,
                label = "animate rotate Icon"
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { isVisibleTopTeacherAndProgress = !isVisibleTopTeacherAndProgress }
                    .scale(MaterialTheme.dimensions.coefficient)
                    .size(54.dp)
                    .rotate(rotateIcon)
            )
        }
        if (isVisibleTopTeacherAndProgress)
            Row(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimensions.grid_5_5)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement
                        .spacedBy(MaterialTheme.dimensions.grid_1_5)
                ) {
                    repeat(monthReport.ratingTeachers.size) { position ->
                        Row(
                            modifier = Modifier
                                .border(
                                    MaterialTheme.dimensions.borderSmall,
                                    MaterialTheme.colors.primary,
                                    MaterialTheme.shapes.small
                                )
                                .height(IntrinsicSize.Min),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${position.inc()}",
                                style = MaterialTheme.typography.overline
                                    .copy(color = MaterialTheme.colors.primary),
                                modifier = Modifier
                                    .padding(
                                        horizontal = MaterialTheme.dimensions.grid_3_5
                                    )
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(MaterialTheme.dimensions.borderSmall)
                                    .background(MaterialTheme.colors.primary)
                            )
                            Text(
                                text = monthReport.ratingTeachers[position],
                                style = MaterialTheme.typography.overline
                                    .copy(color = MaterialTheme.colors.primary),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(
                                        horizontal = MaterialTheme.dimensions.grid_2,
                                        vertical = MaterialTheme.dimensions.grid_1_5,
                                    ),
                                maxLines = 1,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(Modifier.width(MaterialTheme.dimensions.grid_4_5))
                CircularProgressBar(
                    percentage = monthReport.progress,
                    modifier = Modifier
                        .scale(MaterialTheme.dimensions.coefficient)
                        .size(140.dp)
                )
            }
    }
}