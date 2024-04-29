package edu.okei.reward.splash.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import edu.okei.reward.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import edu.okei.reward.common.ui.theme.backgroundStartScreen
import edu.okei.reward.common.view_model.rememberVM
import edu.okei.reward.splash.model.SplashSideEffect
import edu.okei.reward.splash.view_model.SplashVM
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
@Preview(device = "id:pixel_7", showBackground = true)
private fun PreviewSplashScreen() {
    SplashScreen(
        openAuth = {  },
        openAppraiserContent = {}
    )
}


@Composable
fun SplashScreen(
    openAuth: ()-> Unit,
    openAppraiserContent: ()->Unit,
) {
    val splashVM by rememberVM<SplashVM>()
    splashVM.collectSideEffect{sideEffect->
        when(sideEffect){
            SplashSideEffect.OpenAppraiserContent -> openAppraiserContent()
            SplashSideEffect.OpenAuth -> openAuth()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundStartScreen),
        contentAlignment = Alignment.Center,
    ){
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight(),
            contentScale = ContentScale.FillHeight
        )
    }
}