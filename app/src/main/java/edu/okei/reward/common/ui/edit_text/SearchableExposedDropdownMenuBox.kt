package edu.okei.reward.common.ui.edit_text

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.okei.reward.common.ui.click.scaleClick
import edu.okei.reward.common.ui.theme.dimensions

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchableExposedDropdownMenuBox(
    value: String,
    onValueChange: (String) -> Unit,
    suggestionValues: List<String>,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    hint: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.body1.copy(
        color = MaterialTheme.colors.primary,
        textAlign = TextAlign.Start
    ),
    paddingValuesEditText: PaddingValues = PaddingValues(
        vertical = MaterialTheme.dimensions.grid_5_5,
        horizontal = MaterialTheme.dimensions.grid_4
    )
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedEditText(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            hint = hint,
            contentAlignment = Alignment.TopStart,
            textStyle = textStyle,
            paddingValues = paddingValuesEditText,
            trailingIcon = {
                val rotateIcon by animateFloatAsState(
                    if (expanded) 180f else 0f,
                    label = "animate rotate Icon"
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .scale(MaterialTheme.dimensions.coefficient)
                        .size(18.dp)
                        .rotate(rotateIcon)
                )
            }
        )
        if (suggestionValues.isNotEmpty())
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {},
            ) {
                suggestionValues.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onValueChange(item)
                        },
                    ) {
                        Text(
                            text = item,
                            style = textStyle
                        )
                    }
                }
            }
    }
}