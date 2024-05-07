package edu.okei.reward.criteria.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.reward.R
import edu.okei.reward.common.ui.button.FloatingButton
import edu.okei.reward.common.ui.click.alphaClick
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.EventBase
import edu.okei.reward.criteria.model.CriteriaEvent
import edu.okei.reward.criteria.model.CriteriaState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CriteriaInformationPager(
    state: CriteriaState.CriteriaManagement,
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
                CriterionInformationPage(
                    model = state.listCriterion[index]
                )
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
                FloatingButton(icon = painterResource(R.drawable.ic_delete)) {
                    eventBase.onEvent(
                        CriteriaEvent.DeleteCriterion(
                            state.listCriterion[visibleItem].id
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun CriterionInformationPage(
    model: CriterionModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight()
    ) {
        Text(
            text = model.name,
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_2))
        Text(
            text = model.description,
            style = MaterialTheme.typography.subtitle2
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_5_5))
        model.evaluationOptions.forEachIndexed { index, evaluationOptionModel ->
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxWidth(0.9f)
                    .alphaClick { }
                    .padding(vertical = MaterialTheme.dimensions.grid_1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimensions.grid_5_5 * 2)
                        .clip(CircleShape)
                        .border(
                            MaterialTheme.dimensions.borderSmall,
                            MaterialTheme.colors.primary,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = index.inc().toString(),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.primary
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
    }
}