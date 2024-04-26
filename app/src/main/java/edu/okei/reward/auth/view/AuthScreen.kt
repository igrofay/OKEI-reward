package edu.okei.reward.auth.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.okei.reward.R
import edu.okei.reward.auth.model.AuthEvent
import edu.okei.reward.auth.model.AuthSideEffect
import edu.okei.reward.auth.view_model.AuthVM
import edu.okei.reward.common.ui.button.CustomButton
import edu.okei.reward.common.ui.edit_text.EditText
import edu.okei.reward.common.ui.theme.Gray200
import edu.okei.reward.common.ui.theme.backgroundStartScreen
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.rememberVM
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Preview(device = "id:pixel_7", showBackground = true)
@Composable
fun PreviewAuthScreen() {
    AuthScreen(
        openAppraiserContent = {}
    )
}
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.backgroundStartScreen
            )
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(scaleX = 1.5f, scaleY = 1f)
                    .background(
                        Color.White,
                        RoundedCornerShape(
                            topEnd = CornerSize(0),
                            topStart = CornerSize(0),
                            bottomStart = CornerSize(50),
                            bottomEnd = CornerSize(50)
                        )
                    )
            )
            Image(
                painter = if (MaterialTheme.colors.isLight)
                    painterResource(R.drawable.ic_degital_marketing_light)
                else
                    painterResource(R.drawable.ic_degital_marketing_dark),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.7f),
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            EditText(
                value = state.login,
                onValueChange = {
                    authVM.onEvent(AuthEvent.InputLogin(it))
                },
                hint = stringResource(R.string.login),
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                )

            )
            Spacer(
                modifier = Modifier.height(
                    MaterialTheme.dimensions.grid_3_5 * 1.5f
                )
            )
            EditText(
                value = state.password,
                onValueChange = {
                    authVM.onEvent(AuthEvent.InputPassword(it))
                },
                hint = stringResource(R.string.password),
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                ),
                keyboardActions = KeyboardActions { authVM.onEvent(AuthEvent.SignIn) },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(
                modifier = Modifier.height(
                    MaterialTheme.dimensions.grid_5_5 * 1.5f
                )
            )
            CustomButton(
                text = stringResource(R.string.sign_in),
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                textStyle = MaterialTheme.typography.button
                    .copy(color = MaterialTheme.colors.backgroundStartScreen)
            ){
                authVM.onEvent(AuthEvent.SignIn)
            }
        }
    }

}