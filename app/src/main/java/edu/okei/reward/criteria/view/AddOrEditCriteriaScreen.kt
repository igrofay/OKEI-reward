package edu.okei.reward.criteria.view

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import edu.okei.reward.R
import edu.okei.reward.common.ui.appbar.AppTopBar
import edu.okei.reward.common.ui.load.LoadView
import edu.okei.reward.common.view_model.rememberVM
import edu.okei.reward.criteria.model.AddOrEditCriteriaSideEffect
import edu.okei.reward.criteria.model.AddOrEditCriteriaState
import edu.okei.reward.criteria.view_model.AddOrEditCriteriaVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddOrEditCriteriaScreen(
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    val addOrEditVM by rememberVM<AddOrEditCriteriaVM>()
    val state by addOrEditVM.collectAsState()
    addOrEditVM.collectSideEffect {sideEffect->
        when(sideEffect){
            AddOrEditCriteriaSideEffect.EditsCompleted -> onDismissRequest()
            is AddOrEditCriteriaSideEffect.ShowMessage -> Toast
                .makeText(context, sideEffect.message, Toast.LENGTH_SHORT)
                .show()
        }
    }
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