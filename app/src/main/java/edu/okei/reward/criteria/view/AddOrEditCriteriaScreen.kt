package edu.okei.reward.criteria.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import edu.okei.reward.R
import edu.okei.reward.common.ui.appbar.AppTopBar
import edu.okei.reward.common.ui.load.LoadView
import edu.okei.reward.common.view_model.rememberVM
import edu.okei.reward.criteria.model.AddOrEditCriteriaState
import edu.okei.reward.criteria.view_model.AddOrEditCriteriaVM
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun AddOrEditCriteriaScreen(
    onDismissRequest: () -> Unit,
) {
    val addOrEditVM by rememberVM<AddOrEditCriteriaVM>()
    val state by addOrEditVM.collectAsState()
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.adding_criterion)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            when(state){
                AddOrEditCriteriaState.Load -> LoadView()
                is AddOrEditCriteriaState.AddOrEditCriteria -> AddOrEditCriteriaContent(
                    state = state as AddOrEditCriteriaState.AddOrEditCriteria,
                    eventBase = addOrEditVM,
                )
            }
        }
    }
}