package edu.okei.reward.calendar_plan.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import edu.okei.reward.R
import edu.okei.reward.calendar_plan.view_model.CalendarPlanVM
import edu.okei.reward.common.ui.appbar.AppTopBar
import edu.okei.reward.common.ui.button.FloatingButton
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.rememberVM
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CalendarPlanScreen(

) {
    val calendarPlanVM by rememberVM<CalendarPlanVM>()
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.calendar_plan)
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_4),
                modifier = Modifier
                    .padding(WindowInsets.navigationBars.asPaddingValues())
            ) {
                FloatingButton(
                    icon = painterResource(R.drawable.ic_checklist)
                ) {

                }
                FloatingButton(
                    icon = painterResource(R.drawable.ic_currency_ruble)
                ) {

                }
            }
        }
    ) { paddingValues->
        val state by calendarPlanVM.collectAsState()
        CalendarPlanContent(paddingValues, state)
    }
}