package edu.okei.reward.common.ui.edit_text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import edu.okei.reward.common.ui.theme.AppTheme
import edu.okei.reward.common.ui.theme.Blue
import edu.okei.reward.common.ui.theme.Gray200
import edu.okei.reward.common.ui.theme.dimensions


@Preview(device = "id:pixel_7", showBackground = true)
@Composable
private fun PreviewEditText() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Blue),
            contentAlignment = Alignment.Center
        ) {
            EditText(
                value = "",
                onValueChange = {},
                hint = "Login"
            )
        }
    }
}

@Composable
fun EditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    paddingValues: PaddingValues = PaddingValues(MaterialTheme.dimensions.grid_5_5),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = MaterialTheme.typography.body1.copy(
        color = MaterialTheme.colors.primary,
        textAlign = TextAlign.Center
    ),
    borderColor: Color = MaterialTheme.colors.primary
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = true,
        cursorBrush = SolidColor(Gray200),
        textStyle = textStyle,

    ) { innerTextField ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    MaterialTheme.dimensions.borderSmall,
                    borderColor,
                    MaterialTheme.shapes.medium
                )
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            innerTextField()
            if (hint != null && value.isBlank())
                Text(
                    text = hint,
                    style = textStyle.copy(
                        color = textStyle.color.copy(0.6f)
                    ),
                )
        }
    }
}