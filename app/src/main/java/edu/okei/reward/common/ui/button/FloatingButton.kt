package edu.okei.reward.common.ui.button

import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import edu.okei.reward.common.ui.click.scaleClick
import edu.okei.reward.common.ui.theme.dimensions

@Composable
fun FloatingButton(
    icon: Painter,
    modifier: Modifier = Modifier,
    onClick: ()-> Unit
) {
    Box(
        modifier = modifier
            .scaleClick(onClick = onClick)
            .border(
                MaterialTheme.dimensions.borderSmall,
                MaterialTheme.colors.primary,
                CircleShape
            )
            .background(MaterialTheme.colors.background, CircleShape)
            .padding(MaterialTheme.dimensions.grid_3_5),
        contentAlignment = Alignment.Center,
    ){
        Icon(
            painter = icon ,
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
//                .scale()
                .size(24.dp * MaterialTheme.dimensions.coefficient)
        )
    }

}