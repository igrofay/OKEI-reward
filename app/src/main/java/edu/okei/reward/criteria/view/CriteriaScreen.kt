package edu.okei.reward.criteria.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.okei.reward.R
import edu.okei.reward.common.ui.appbar.AppTopBar
import edu.okei.reward.common.ui.button.FloatingButton
import edu.okei.reward.common.ui.load.LoadView
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.rememberVM
import edu.okei.reward.criteria.model.CriteriaEvent
import edu.okei.reward.criteria.model.CriteriaSideEffect
import edu.okei.reward.criteria.model.CriteriaState
import edu.okei.reward.criteria.view_model.CriteriaVM
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CriteriaScreen(
    addNewCriterion: () -> Unit
) {

    val criteriaVM by rememberVM<CriteriaVM>()
    val state by criteriaVM.collectAsState()
    val listState = rememberLazyListState()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val pagerState = rememberPagerState {
        when (val targetState = state) {
            is CriteriaState.CriteriaManagement -> targetState.listCriterion.size
            CriteriaState.Load -> 0
            is CriteriaState.TeacherEvaluationAccordingToCriteria -> targetState.listCriterion.size
        }
    }
    val coroutine = rememberCoroutineScope()
    criteriaVM.collectSideEffect { sideEffect ->
        when (sideEffect) {
            CriteriaSideEffect.OpenAddCriterion -> addNewCriterion()
            is CriteriaSideEffect.OpenFullCriteriaInformation -> coroutine.launch {
                scaffoldState.bottomSheetState.expand()
                pagerState.animateScrollToPage(sideEffect.position)
            }
        }
    }
    BottomSheetScaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.criteria)
            )
        },
        floatingActionButton = {
            if (
                state is CriteriaState.CriteriaManagement &&
                scaffoldState.bottomSheetState.targetValue != BottomSheetValue.Expanded
            )
                FloatingButton(
                    icon = painterResource(R.drawable.ic_add),
                    modifier = Modifier
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                ) {
                    criteriaVM.onEvent(CriteriaEvent.AddCriterion)
                }
        },
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = MaterialTheme.dimensions.grid_3_5)
                    .scale(MaterialTheme.dimensions.coefficient)
                    .height(6.dp)
                    .width(60.dp)
                    .background(Color.Gray.copy(0.8f), CircleShape)
            )
            when (state) {
                CriteriaState.Load -> LoadView()
                is CriteriaState.CriteriaManagement -> CriteriaInformationPager(
                    state as CriteriaState.CriteriaManagement,
                    pagerState, criteriaVM
                )
                is CriteriaState.TeacherEvaluationAccordingToCriteria -> EvaluatingTeacherOnCriteriaPager(
                    state as CriteriaState.TeacherEvaluationAccordingToCriteria,
                    pagerState,
                    criteriaVM,
                )
            }
        },
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetPeekHeight = 0.dp
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

                is CriteriaState.TeacherEvaluationAccordingToCriteria -> CriteriaForEvaluatingTeacherContent(
                    state as CriteriaState.TeacherEvaluationAccordingToCriteria,
                    criteriaVM,
                    listState
                )
            }
        }

    }
}