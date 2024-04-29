package edu.okei.reward.calendar_plan.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.okei.reward.calendar_plan.model.CalendarPlanState
import edu.okei.reward.common.ui.load.LoadView

@Composable
fun CalendarPlanContent(
    paddingValues: PaddingValues,
    state: CalendarPlanState,
) {
    AnimatedContent(
        targetState = state,
        label = "",
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) { targetState->
        when(targetState){
            CalendarPlanState.Load -> LoadView()
            is CalendarPlanState.MonthProgress -> ListMonthlyProgress(targetState)
        }
    }


}