package edu.okei.reward.teachers.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import edu.okei.reward.R
import edu.okei.reward.common.ui.appbar.AppTopBar
import edu.okei.reward.common.ui.button.FloatingButton
import edu.okei.reward.common.ui.load.LoadView
import edu.okei.reward.common.view_model.rememberVM
import edu.okei.reward.teachers.model.TeachersState
import edu.okei.reward.teachers.view_model.TeachersVM
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun TeachersScreen() {
    val teachersVM by rememberVM<TeachersVM>()
    val state by teachersVM.collectAsState()
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.list_of_teachers)
            )
        },
        floatingActionButton = {
            if (state is TeachersState.TeacherManagement)
                FloatingButton(
                    icon = painterResource(R.drawable.ic_add),
                    modifier = Modifier
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                ) {

                }
        }
    ) {
        AnimatedContent(
            targetState = state,
            label = "",
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) { targetState ->
            when (targetState) {
                TeachersState.Load -> LoadView()
                is TeachersState.TeacherManagement ->
                    TeacherManagementContent(targetState)
            }
        }
    }

}