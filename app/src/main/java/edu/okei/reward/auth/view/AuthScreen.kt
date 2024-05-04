package edu.okei.reward.auth.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import edu.okei.reward.auth.model.AuthSideEffect
import edu.okei.reward.auth.view_model.AuthVM
import edu.okei.reward.common.view_model.rememberVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun AuthScreen(
    openAppraiserContent: ()->Unit
) {
    val authVM by rememberVM<AuthVM>()
    val state by authVM.collectAsState()

    val context = LocalContext.current
    authVM.collectSideEffect{sideEffect->
        when(sideEffect){
            AuthSideEffect.OpenAppraiserContent -> openAppraiserContent()
            is AuthSideEffect.ShowMessage -> Toast
                .makeText(
                    context,
                    sideEffect.message,
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }
    AuthContent(state, authVM)
}