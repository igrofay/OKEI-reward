package edu.okei.reward.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import edu.okei.reward.common.model.TypeDevise

@Composable
fun identifyDevice() : TypeDevise{
    val screenWidthDp  = LocalConfiguration.current.screenWidthDp
    val type = remember(screenWidthDp) {
        when{
            screenWidthDp<=TypeDevise.Phone.maxWidthDp->TypeDevise.Phone
            screenWidthDp<=TypeDevise.Table.maxWidthDp-> TypeDevise.Table
            screenWidthDp<=TypeDevise.TV.maxWidthDp-> TypeDevise.TV
            else -> TypeDevise.Large
        }
    }
    return type
}