package edu.okei.reward.calculation_of_reward.view

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.okei.reward.R
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardEvent
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardSideEffect
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardState
import edu.okei.reward.calculation_of_reward.model.CreateXLSXDocumentContract
import edu.okei.reward.calculation_of_reward.view_model.CalculationOfRewardVM
import edu.okei.reward.calendar_plan.model.CalendarPlanEvent
import edu.okei.reward.common.ui.appbar.AppTopBar
import edu.okei.reward.common.ui.button.FloatingButton
import edu.okei.reward.common.ui.load.LoadView
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.rememberVM
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalculationOfRewardScreen() {
    val calculationOfRewardVM by rememberVM<CalculationOfRewardVM>()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutine = rememberCoroutineScope()
    val state by calculationOfRewardVM.collectAsState()
    val context = LocalContext.current
    calculationOfRewardVM.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is CalculationOfRewardSideEffect.ShowMessage -> Toast
                .makeText(context, sideEffect.message, Toast.LENGTH_SHORT)
                .show()

            CalculationOfRewardSideEffect.OpenReportData -> coroutine.launch {
                scaffoldState.bottomSheetState.expand()
            }
        }
    }

    val activityLauncher = rememberLauncherForActivityResult(CreateXLSXDocumentContract()) { uri ->
        uri?.let {
            calculationOfRewardVM
                .onEvent(
                    CalculationOfRewardEvent
                        .DownloadReport(it)
                )
        }
    }
    val lazyListState = rememberLazyListState()
    BottomSheetScaffold(
        topBar = {
            AppTopBar(
                title = when (state) {
                    is CalculationOfRewardState.CreateReportForMonth ->
                        getMonthName((state as CalculationOfRewardState.CreateReportForMonth).monthIndex)
                    is CalculationOfRewardState.ListMonthsOfReward ->
                        stringResource(R.string.calculation_of_reward)

                    CalculationOfRewardState.Load ->
                        stringResource(R.string.calculation_of_reward)

                    is CalculationOfRewardState.RewardReportForMonth ->
                        stringResource(R.string.list_teacher)
                }
            )
        },
        floatingActionButton = {
            if (
                state is CalculationOfRewardState.RewardReportForMonth &&
                !lazyListState.isScrollInProgress &&
                scaffoldState.bottomSheetState.targetValue != BottomSheetValue.Expanded
            )
                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_4),
                    modifier = Modifier
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                ) {
                    FloatingButton(
                        icon = painterResource(R.drawable.ic_download)
                    ) {
                        activityLauncher.launch(
                            (state as CalculationOfRewardState.RewardReportForMonth).fileName
                        )
                    }
                    FloatingButton(
                        icon = painterResource(R.drawable.ic_query_stats)
                    ) {
                        calculationOfRewardVM.onEvent(CalculationOfRewardEvent.SeeReportData)
                    }
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
                is CalculationOfRewardState.RewardReportForMonth -> ReportDataFieldsContent(
                    (state as CalculationOfRewardState.RewardReportForMonth).report.reportData
                )

                else -> Box(modifier = Modifier)
            }
        },
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetPeekHeight = 0.dp
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (state) {
                CalculationOfRewardState.Load -> LoadView()
                is CalculationOfRewardState.ListMonthsOfReward -> ListMonthsOfRewardContent(
                    state as CalculationOfRewardState.ListMonthsOfReward,
                    calculationOfRewardVM,
                    lazyListState
                )

                is CalculationOfRewardState.CreateReportForMonth -> ReportDataInputFieldsContent(
                    state as CalculationOfRewardState.CreateReportForMonth,
                    calculationOfRewardVM,
                    lazyListState
                )

                is CalculationOfRewardState.RewardReportForMonth -> RewardReportForMonthContent(
                    state as CalculationOfRewardState.RewardReportForMonth,
                    lazyListState
                )
            }
        }
    }
}

@Composable
private fun getMonthName(monthIndex: Int): String {
    val itemPosition = monthIndex - 1
    if (itemPosition !in 0..11) return "Недопустимый номер месяца"
    val monthArray = stringArrayResource(R.array.months_array)
    return monthArray.getOrElse(itemPosition) { "Недопустимый номер месяца" }
}