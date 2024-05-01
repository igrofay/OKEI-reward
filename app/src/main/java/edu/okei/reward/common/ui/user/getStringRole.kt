package edu.okei.reward.common.ui.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import edu.okei.core.domain.model.user.UserRole
import edu.okei.reward.R

@Composable
fun getStringRole(role: UserRole) : String {
    return when(role){
        UserRole.Undefined -> stringResource(R.string.undefined)
        UserRole.Director -> stringResource(R.string.director)
        UserRole.Appraiser -> stringResource(R.string.appraiser)
        UserRole.Teacher -> stringResource(R.string.teacher)
    }
}