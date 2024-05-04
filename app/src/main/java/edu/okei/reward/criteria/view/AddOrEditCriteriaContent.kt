package edu.okei.reward.criteria.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import edu.okei.reward.R
import edu.okei.reward.common.ui.edit_text.OutlinedEditText
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.EventBase
import edu.okei.reward.criteria.model.AddOrEditCriteriaEvent
import edu.okei.reward.criteria.model.AddOrEditCriteriaState

@Composable
fun AddOrEditCriteriaContent(
    state: AddOrEditCriteriaState.AddOrEditCriteria,
    eventBase: EventBase<AddOrEditCriteriaEvent>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = MaterialTheme.dimensions.grid_4
            )
    ) {
        OutlinedEditText(
            value = state.nameCriterion,
            onValueChange = { eventBase.onEvent(AddOrEditCriteriaEvent.InputName(it)) },
            hint = stringResource(R.string.name_criterion),
            textStyle = MaterialTheme.typography.body2
                .copy(color = MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            singleLine = false,
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart,
            paddingValues = PaddingValues(
                vertical = MaterialTheme.dimensions.grid_5_5,
                horizontal = MaterialTheme.dimensions.grid_4
            )
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_4))
    }
}