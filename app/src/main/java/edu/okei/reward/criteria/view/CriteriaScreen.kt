package edu.okei.reward.criteria.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import edu.okei.reward.criteria.model.CriteriaEvent
import edu.okei.reward.criteria.model.CriteriaSideEffect
import edu.okei.reward.criteria.model.CriteriaState
import edu.okei.reward.criteria.view_model.CriteriaVM
import edu.okei.reward.teachers.model.TeachersEvent
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun CriteriaScreen(
    addNewCriterion: ()-> Unit
) {
    val criteriaVM by rememberVM<CriteriaVM>()
    val state by criteriaVM.collectAsState()
    val listState = rememberLazyListState()
    criteriaVM.collectSideEffect{sideEffect ->
        when(sideEffect){
            CriteriaSideEffect.OpenAddCriterion -> addNewCriterion()
        }
    }
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.criteria)
            )
        },
        floatingActionButton = {
            if (state is CriteriaState.CriteriaManagement)
                FloatingButton(
                    icon = painterResource(R.drawable.ic_add),
                    modifier = Modifier
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                ) {
                    criteriaVM.onEvent(CriteriaEvent.AddCriterion)
                }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (state) {
                CriteriaState.Load -> LoadView()
                is CriteriaState.CriteriaManagement -> CriteriaManagementContent(
                    state as CriteriaState.CriteriaManagement,
                    criteriaVM, listState
                )
            }
        }
    }
}