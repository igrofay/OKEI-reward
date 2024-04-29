package edu.okei.reward.nav.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import edu.okei.reward.calendar_plan.view.CalendarPlanScreen
import edu.okei.reward.common.ui.appbar.AppTopBar
import edu.okei.reward.nav.model.AppraiserContentRouting


fun NavGraphBuilder.appraiseContentGraph(
    navController: NavController
) {
    navigation(
        startDestination = AppraiserContentRouting.CalendarPlan.route,
        route = AppraiserContentRouting.route
    ){
        composable(AppraiserContentRouting.CalendarPlan.route){
            CalendarPlanScreen()
        }
        composable(AppraiserContentRouting.Teachers.route){

        }
        composable(AppraiserContentRouting.Evaluation.route){

        }
    }
}