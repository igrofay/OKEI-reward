package edu.okei.reward.criteria.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import edu.okei.core.domain.model.criteria.CriterionModel
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import edu.okei.reward.common.ui.theme.dimensions

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SerialNumberTabRow(
    pagerState: PagerState,
    listCriterion: List<CriterionModel>,
    onClickTab: (index: Int) -> Unit,
) {
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
            )
        },
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        listCriterion.forEachIndexed { index, criterion ->
            val selected by remember {
                derivedStateOf { pagerState.currentPage == index }
            }
            Tab(
                selected = selected,
                onClick = { onClickTab(index) },
                selectedContentColor = MaterialTheme.colors.primary,
            ) {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimensions.grid_5_5 * 3)
                        .padding(MaterialTheme.dimensions.grid_3)
                        .clip(CircleShape)
                        .background(
                            if (selected) MaterialTheme.colors.primary else Color.Transparent
                        )
                        .border(
                            MaterialTheme.dimensions.borderSmall,
                            if (selected) Color.Transparent else MaterialTheme.colors.primary,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = criterion.serialNumber,
                        style = MaterialTheme.typography.caption,
                        color = if (selected) MaterialTheme.colors.background else MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}