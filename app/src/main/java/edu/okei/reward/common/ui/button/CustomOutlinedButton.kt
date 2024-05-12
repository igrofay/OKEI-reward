package edu.okei.reward.common.ui.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import edu.okei.reward.common.ui.click.alphaClick
import edu.okei.reward.common.ui.theme.dimensions

@Composable
fun CustomOutlinedButton(
    label: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.body2
        .copy(MaterialTheme.colors.primary),
    paddingValues: PaddingValues = PaddingValues(
        vertical = MaterialTheme.dimensions.grid_3
    ),
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .alphaClick { onClick() }
            .fillMaxWidth()
            .border(
                MaterialTheme.dimensions.borderSmall,
                MaterialTheme.colors.primary,
                MaterialTheme.shapes.small,
            )
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = style
        )
    }
}