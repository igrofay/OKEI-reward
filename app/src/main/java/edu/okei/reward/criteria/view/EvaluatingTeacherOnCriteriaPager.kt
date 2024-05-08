package edu.okei.reward.criteria.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.model.teacher.TeacherEvaluationModel
import edu.okei.reward.R
import edu.okei.reward.common.ui.click.alphaClick
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.EventBase
import edu.okei.reward.criteria.model.CriteriaEvent
import edu.okei.reward.criteria.model.CriteriaState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EvaluatingTeacherOnCriteriaPager(
    state: CriteriaState.TeacherEvaluationAccordingToCriteria,
    pagerState: PagerState,
    eventBase: EventBase<CriteriaEvent>
) {
    val coroutine = rememberCoroutineScope()
    val visibleItem by remember {
        derivedStateOf { pagerState.currentPage }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SerialNumberTabRow(
            pagerState = pagerState,
            listCriterion = state.listCriterion
        ) { index ->
            coroutine.launch {
                pagerState.animateScrollToPage(index)
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_3_5))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                top = MaterialTheme.dimensions.grid_5_5
            )
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ){
                val criterion = state.listCriterion[index]
                CriterionInformationPage(
                    criterion,
                    state.alreadyPostedTeacherEvaluations[criterion.id]
                ){id->
                    eventBase.onEvent(CriteriaEvent.TeacherEvaluation(id))
                }
            }
        }
        CriteriaBottomNavBar(
            onClickBack = {
                if (visibleItem - 1 >= 0)
                    coroutine.launch {
                        pagerState.animateScrollToPage(visibleItem - 1)
                    }
            },
            onClickNext = {
                if (visibleItem + 1 < state.listCriterion.size)
                    coroutine.launch {
                        pagerState.animateScrollToPage(visibleItem + 1)
                    }
            },
            label = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .border(
                            MaterialTheme.dimensions.borderSmall,
                            MaterialTheme.colors.primary,
                            CircleShape,
                        )
                        .padding(MaterialTheme.dimensions.grid_4),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = state.teacherName,
                        style = MaterialTheme.typography.body1
                            .copy(color = MaterialTheme.colors.primary),
                    )
                }
            }
        )
    }
}

@Composable
private fun CriterionInformationPage(
    criterion: CriterionModel,
    teacherEvaluation: TeacherEvaluationModel?,
    onClickEval:(evalId: String)->Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight()
    ) {
        Text(
            text = criterion.name,
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_2))
        Text(
            text = criterion.description,
            style = MaterialTheme.typography.subtitle2
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_5_5))
        criterion.evaluationOptions.forEachIndexed { index, evaluationOptionModel ->
            val isSelected = remember(teacherEvaluation) {
                teacherEvaluation?.evaluationId == evaluationOptionModel.id
            }
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .alphaClick { onClickEval(evaluationOptionModel.id) }
                    .fillMaxWidth(0.9f)
                    .padding(vertical = MaterialTheme.dimensions.grid_1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimensions.grid_5_5 * 2)
                        .clip(CircleShape)
                        .border(
                            MaterialTheme.dimensions.borderSmall,
                            if (isSelected) Color.Transparent else MaterialTheme.colors.primary,
                            CircleShape
                        )
                        .background(
                            if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = index.inc().toString(),
                        style = MaterialTheme.typography.body1,
                        color = if (isSelected) MaterialTheme.colors.background else MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.width(MaterialTheme.dimensions.grid_5_5))
                Text(
                    text = evaluationOptionModel.description,
                    style = MaterialTheme.typography.body1,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_5))
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${stringResource(R.string.appraiser)}: ${teacherEvaluation?.appraiserName ?: stringResource(R.string.no)}",
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            text = "${stringResource(R.string.last_change)}: ${teacherEvaluation?.date ?: stringResource(R.string.no)}",
            style = MaterialTheme.typography.subtitle2
        )
    }
}