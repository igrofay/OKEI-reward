package edu.okei.reward.criteria.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import edu.okei.reward.R
import edu.okei.reward.common.ui.click.scaleClick
import edu.okei.reward.common.ui.theme.dimensions

@Composable
fun CriteriaBottomNavBar(
    onClickBack: () -> Unit,
    onClickNext: ()-> Unit,
    label: (@Composable BoxScope.()->Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(IntrinsicSize.Min)
            .padding(vertical = MaterialTheme.dimensions.grid_5_5),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Box(
            modifier = Modifier
                .scaleClick { onClickBack() }
                .border(
                    MaterialTheme.dimensions.borderSmall,
                    MaterialTheme.colors.primary,
                    CircleShape,
                )
                .padding(MaterialTheme.dimensions.grid_3_5),
            contentAlignment = Alignment.Center,
        ){
            Icon(
                painter = painterResource(R.drawable.ic_right) ,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .scale(MaterialTheme.dimensions.coefficient)
                    .size(24.dp)
            )
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ){
            label?.invoke(this)
        }
        Box(
            modifier = Modifier
                .scaleClick { onClickNext() }
                .border(
                    MaterialTheme.dimensions.borderSmall,
                    MaterialTheme.colors.primary,
                    CircleShape,
                )
                .padding(MaterialTheme.dimensions.grid_3_5),
            contentAlignment = Alignment.Center,
        ){
            Icon(
                painter = painterResource(R.drawable.ic_left) ,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .scale(MaterialTheme.dimensions.coefficient)
                    .size(24.dp)
            )
        }
    }
}