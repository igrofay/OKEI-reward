package edu.okei.reward.calculation_of_reward.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import edu.okei.core.domain.model.statistics.TeachersRewardReportModel
import edu.okei.core.domain.model.user.shortenFullName
import edu.okei.reward.R
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardState
import edu.okei.reward.common.ui.theme.dimensions

@Composable
fun RewardReportForMonthContent(
    state: CalculationOfRewardState.RewardReportForMonth,
    listState: LazyListState,
) {
    LazyColumn(
        modifier = Modifier,
        state = listState,
        contentPadding = PaddingValues(
            top = MaterialTheme.dimensions.grid_5_5,
            start = MaterialTheme.dimensions.grid_4,
            end = MaterialTheme.dimensions.grid_4,
            bottom = (WindowInsets.navigationBars
                .asPaddingValues().calculateBottomPadding() +
                    MaterialTheme.dimensions.grid_5_5)
        ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_3_5),
    ){
        items(state.report.listTeacherReward){model->
            TeacherRewardItem(model)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TeacherRewardItem(
    model: TeachersRewardReportModel.TeacherRewardModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                MaterialTheme.dimensions.borderSmall,
                MaterialTheme.colors.primary,
                MaterialTheme.shapes.medium
            )
            .padding(
                horizontal = MaterialTheme.dimensions.grid_5_5 * 1.5f,
                vertical = MaterialTheme.dimensions.grid_5_5,
            ),
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = shortenFullName(model.fullname),
                style = MaterialTheme.typography.h5
                    .copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.W300
                    ),
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .basicMarquee()
            )
            Text(
                text = "${stringResource(R.string.count_of_points)}: ${model.countPoints}",
                style = MaterialTheme.typography.overline
                    .copy(color = MaterialTheme.colors.primary),
                maxLines = 1,

            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.dimensions.grid_3))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(IntrinsicSize.Min),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_1_5)
        ){
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.dimensions.borderSmall)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
            )
            Text(
                text = "${model.reward} ${stringResource(R.string.rub)}",
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .padding(horizontal = MaterialTheme.dimensions.grid_2),
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.dimensions.borderSmall)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
            )
        }
    }
}