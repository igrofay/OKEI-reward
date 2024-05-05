package edu.okei.reward.criteria.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.okei.core.domain.model.criteria.NewCriterionModel
import edu.okei.reward.R
import edu.okei.reward.common.ui.button.CustomButton
import edu.okei.reward.common.ui.button.FloatingButton
import edu.okei.reward.common.ui.click.alphaClick
import edu.okei.reward.common.ui.edit_text.OutlinedEditText
import edu.okei.reward.common.ui.edit_text.SearchableExposedDropdownMenuBox
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
            .padding(bottom = MaterialTheme.dimensions.grid_3)
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = MaterialTheme.dimensions.grid_4,
                    vertical = MaterialTheme.dimensions.grid_5
                )
        ) {
            OutlinedEditText(
                value = state.serialNumber,
                onValueChange = { eventBase.onEvent(AddOrEditCriteriaEvent.InputSerialNumber(it)) },
                hint = stringResource(R.string.serial_number),
                textStyle = MaterialTheme.typography.body1
                    .copy(color = MaterialTheme.colors.primary),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart,
                paddingValues = PaddingValues(
                    vertical = MaterialTheme.dimensions.grid_5_5,
                    horizontal = MaterialTheme.dimensions.grid_4
                ),
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_4_5))
            OutlinedEditText(
                value = state.nameCriterion,
                onValueChange = { eventBase.onEvent(AddOrEditCriteriaEvent.InputName(it)) },
                hint = stringResource(R.string.name_of_indicators),
                textStyle = MaterialTheme.typography.body1
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
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_4_5))
            SearchableExposedDropdownMenuBox(
                value = state.description,
                onValueChange = { eventBase.onEvent(AddOrEditCriteriaEvent.InputDescription(it)) },
                suggestionValues = state.examplesOfDescriptions,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
                singleLine = false,
                textStyle = MaterialTheme.typography.body1
                    .copy(color = MaterialTheme.colors.primary),
                hint = stringResource(R.string.name_of_criteria)
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.dimensions.grid_5_5)
            )
            Text(
                text = stringResource(R.string.evaluation_options),
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h6
                    .copy(
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary
                    )
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.dimensions.grid_4)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    MaterialTheme.dimensions.grid_3
                )
            ) {
                state.evaluationOptions.forEachIndexed { index, model ->
                    EvaluationOptionItem(
                        model = model,
                        onDescriptionChange = {
                            eventBase.onEvent(
                                AddOrEditCriteriaEvent
                                    .InputDescriptionEvaluationOption(index, it)
                            )
                        },
                        onDelete = {
                            eventBase.onEvent(AddOrEditCriteriaEvent.DeleteEvaluationOption(index))
                        },
                        incrementCountPoints = {
                            eventBase.onEvent(
                                AddOrEditCriteriaEvent.IncrementCountPointersInEvaluationOption(
                                    index
                                )
                            )
                        },
                        decrementCountPoints = {
                            eventBase.onEvent(
                                AddOrEditCriteriaEvent.DecrementCountPointersInEvaluationOption(
                                    index
                                )
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_4))
            FloatingButton(
                icon = painterResource(R.drawable.ic_add),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                eventBase.onEvent(AddOrEditCriteriaEvent.AddEvaluationOption)
            }
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.dimensions.grid_5_5)
            )
        }
        CustomButton(
            text = stringResource(R.string.create),
            modifier = Modifier.padding(horizontal = MaterialTheme.dimensions.grid_4)
        ) {
            eventBase.onEvent(AddOrEditCriteriaEvent.AddOrEditCriterion)
        }
    }


}

@Composable
private fun EvaluationOptionItem(
    model: NewCriterionModel.EvaluationOptionModel,
    onDescriptionChange: (String) -> Unit,
    onDelete: () -> Unit,
    incrementCountPoints: () -> Unit,
    decrementCountPoints: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                MaterialTheme.dimensions.borderSmall,
                MaterialTheme.colors.primary,
                MaterialTheme.shapes.small,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            BasicTextField(
                value = model.description,
                onValueChange = onDescriptionChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = MaterialTheme.dimensions.grid_4,
                        horizontal = MaterialTheme.dimensions.grid_4
                    ),
                textStyle = MaterialTheme.typography.body1
                    .copy(color = MaterialTheme.colors.primary),
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                singleLine = true
            ){innerTextField ->
                if (model.description.isBlank())
                    Text(
                        text = stringResource(R.string.indicator_value),
                        style = MaterialTheme.typography.body1
                            .copy(color = MaterialTheme.colors.primary.copy(alpha = 0.6f)),
                    )
                innerTextField()
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimensions.borderSmall)
                    .background(MaterialTheme.colors.primary)
            )
            Row(
                modifier = Modifier
                    .padding(
                        vertical = MaterialTheme.dimensions.grid_3_5,
                        horizontal = MaterialTheme.dimensions.grid_4
                    )
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${stringResource(R.string.number_of_points)}: ${model.countPoints}",
                    style = MaterialTheme.typography.body2
                        .copy(color = MaterialTheme.colors.primary),
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .alphaClick { decrementCountPoints() }
                        .scale(MaterialTheme.dimensions.coefficient)
                        .size(28.dp)
                )
                Spacer(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.dimensions.grid_2,
                        )
                        .width(MaterialTheme.dimensions.borderSmall)
                        .fillMaxHeight()
                        .background(MaterialTheme.colors.primary)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .alphaClick { incrementCountPoints() }
                        .scale(MaterialTheme.dimensions.coefficient)
                        .size(28.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .width(MaterialTheme.dimensions.borderSmall)
                .fillMaxHeight()
                .background(MaterialTheme.colors.primary)
        )
        Icon(
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimensions.grid_4)
                .alphaClick { onDelete() }
                .scale(MaterialTheme.dimensions.coefficient)
                .size(28.dp)
        )
    }
}