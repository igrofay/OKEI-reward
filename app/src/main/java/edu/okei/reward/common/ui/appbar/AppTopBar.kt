package edu.okei.reward.common.ui.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import edu.okei.reward.common.ui.theme.dimensions

@Composable
fun AppTopBar(
    title: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimensions.grid_4_5)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Spacer(
            modifier = Modifier
                .align(Alignment.Top)
                .weight(1f)
                .height(MaterialTheme.dimensions.borderSmall)
                .background(MaterialTheme.colors.primary)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .border(
                    MaterialTheme.dimensions.borderSmall,
                    MaterialTheme.colors.primary,
                    RoundedCornerShape(
                        topStart = CornerSize(0),
                        topEnd = MaterialTheme.shapes.medium.topStart,
                        bottomEnd = CornerSize(0),
                        bottomStart = MaterialTheme.shapes.medium.topStart
                    )
                )
                .padding(
                    vertical = MaterialTheme.dimensions.grid_4_5
                ),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = title,
                style = MaterialTheme.typography.h6
                    .copy(color = MaterialTheme.colors.primary),
            )
        }
        Spacer(
            modifier = Modifier
                .align(Alignment.Bottom)
                .weight(1f)
                .height(MaterialTheme.dimensions.borderSmall)
                .background(MaterialTheme.colors.primary)
        )
    }
}