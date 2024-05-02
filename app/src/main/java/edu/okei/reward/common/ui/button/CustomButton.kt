package edu.okei.reward.common.ui.button

import androidx.compose.foundation.background
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
import edu.okei.reward.common.ui.theme.Black900
import edu.okei.reward.common.ui.theme.Gray200
import edu.okei.reward.common.ui.theme.dimensions

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Black900,
    textStyle: TextStyle = MaterialTheme.typography.button
        .copy(color = MaterialTheme.colors.background),
    background: Color = MaterialTheme.colors.primary,
    contentPadding: PaddingValues = PaddingValues(MaterialTheme.dimensions.grid_5_5),
    onClick: ()->Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .alphaClick(onClick = onClick)
            .background(
                color = background,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(contentPadding),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = text,
            style = textStyle,
        )
    }
}