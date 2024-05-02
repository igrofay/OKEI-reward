package edu.okei.reward.teachers.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import edu.okei.reward.R
import edu.okei.reward.common.ui.appbar.AppTopBar
import edu.okei.reward.common.ui.button.FloatingButton
import edu.okei.reward.common.ui.load.LoadView
import edu.okei.reward.common.view_model.rememberVM
import edu.okei.reward.teachers.model.TeachersEvent
import edu.okei.reward.teachers.model.TeachersState
import edu.okei.reward.teachers.view_model.TeachersVM
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun TeachersScreen() {
    val teachersVM by rememberVM<TeachersVM>()
    val state by teachersVM.collectAsState()
    val listState = rememberLazyListState()
    val isVisibleFirstItem by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.list_of_teachers)
            )
        },
        floatingActionButton = {
            if (state is TeachersState.TeacherManagement && isVisibleFirstItem)
                FloatingButton(
                    icon = painterResource(R.drawable.ic_add),
                    modifier = Modifier
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                ) {
                    teachersVM.onEvent(TeachersEvent.AddTeacher)
                }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (state) {
                TeachersState.Load -> LoadView()
                is TeachersState.TeacherManagement ->
                    TeacherManagementContent(
                        state as TeachersState.TeacherManagement,
                        teachersVM,
                        listState
                    )
            }
        }
    }

}