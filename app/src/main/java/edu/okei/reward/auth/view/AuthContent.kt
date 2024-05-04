package edu.okei.reward.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import edu.okei.reward.R
import edu.okei.reward.auth.model.AuthEvent
import edu.okei.reward.auth.model.AuthState
import edu.okei.reward.common.ui.button.CustomButton
import edu.okei.reward.common.ui.edit_text.OutlinedEditText
import edu.okei.reward.common.ui.theme.AppTheme
import edu.okei.reward.common.ui.theme.Gray200
import edu.okei.reward.common.ui.theme.backgroundStartScreen
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.EventBase

@Preview(device = "id:pixel_7", showBackground = true)
@Composable
private fun PreviewAuthScreen() {
    AppTheme {
        AuthContent(
            state = AuthState(),
            event = {}
        )
    }
}

@Composable
fun AuthContent(
    state: AuthState,
    event: EventBase<AuthEvent>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.backgroundStartScreen
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
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
            OutlinedEditText(
                value = state.login,
                onValueChange = {
                    event.onEvent(AuthEvent.InputLogin(it))
                },
                hint = stringResource(R.string.login),
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                ),
                borderColor = Gray200,
                textStyle = MaterialTheme.typography.body1.copy(
                    color = Gray200,
                    textAlign = TextAlign.Center
                ),
            )
            Spacer(
                modifier = Modifier.height(
                    MaterialTheme.dimensions.grid_3 * 1.5f
                )
            )
            OutlinedEditText(
                value = state.password,
                onValueChange = {
                    event.onEvent(AuthEvent.InputPassword(it))
                },
                hint = stringResource(R.string.password),
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                ),
                keyboardActions = KeyboardActions { event.onEvent(AuthEvent.SignIn) },
                visualTransformation = PasswordVisualTransformation(),
                borderColor = Gray200,
                textStyle = MaterialTheme.typography.body1.copy(
                    color = Gray200,
                    textAlign = TextAlign.Center
                ),
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
                    .copy(color = MaterialTheme.colors.backgroundStartScreen),
                background = Gray200
            ) {
                event.onEvent(AuthEvent.SignIn)
            }
        }

    }

}