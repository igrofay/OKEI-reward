package edu.okei.reward.criteria.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.reward.R
import edu.okei.reward.common.ui.edit_text.OutlinedEditText
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.EventBase
import edu.okei.reward.criteria.model.CriteriaEvent
import edu.okei.reward.criteria.model.CriteriaState

@Composable
fun CriteriaManagementContent(
    state: CriteriaState.CriteriaManagement,
    eventBase: EventBase<CriteriaEvent>,
    listState: LazyListState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedEditText(
            value = state.searchText,
            onValueChange = {
                eventBase.onEvent(CriteriaEvent.InputSearch(it))
            },
            hint = stringResource(R.string.search),
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimensions.grid_4
                ),
            textStyle = MaterialTheme.typography.body2
                .copy(
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(
                top = MaterialTheme.dimensions.grid_5_5,
                start = MaterialTheme.dimensions.grid_4,
                end = MaterialTheme.dimensions.grid_4,
                bottom = (WindowInsets.navigationBars
                    .asPaddingValues().calculateBottomPadding() +
                        MaterialTheme.dimensions.grid_5_5 * 4f)
            ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_3_5),
            state = listState,
        ) {
            items(state.listCriterion) {
                CriterionItem(
                    model = it
                )
            }
        }
    }
}

@Composable
private fun CriterionItem(
    model: CriterionModel
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
                .size(MaterialTheme.dimensions.grid_5_5 * 4f)
                .padding(MaterialTheme.dimensions.grid_5)
                .border(
                    MaterialTheme.dimensions.borderSmall,
                    MaterialTheme.colors.primary,
                    CircleShape
                )
                .padding(MaterialTheme.dimensions.grid_3_5),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = model.serialNumber,
                style = MaterialTheme.typography.caption
                    .copy(color = MaterialTheme.colors.primary)
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(MaterialTheme.dimensions.borderSmall)
                .background(MaterialTheme.colors.primary)
        )
        Text(
            text = model.name,
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = MaterialTheme.dimensions.grid_5_5,
                    vertical = MaterialTheme.dimensions.grid_4
                ),
            style = MaterialTheme.typography.body2
        )
    }
}