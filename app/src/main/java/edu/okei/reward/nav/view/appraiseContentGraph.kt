package edu.okei.reward.nav.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import edu.okei.reward.calendar_plan.view.CalendarPlanScreen
import edu.okei.reward.nav.model.AppraiserContentRouting
import edu.okei.reward.teachers.view.TeachersScreen


fun NavGraphBuilder.appraiseContentGraph(
    navController: NavController
) {
    navigation(
        startDestination = AppraiserContentRouting.CalendarPlan.route,
        route = AppraiserContentRouting.route
    ){
        composable(AppraiserContentRouting.CalendarPlan.route){
            CalendarPlanScreen(
                openCriteria={
                    navController
                        .navigate(AppraiserContentRouting.Criteria.route)
                },
                openTeachers={
                    navController
                        .navigate(AppraiserContentRouting.Teachers.getRoute())
                }
            )
        }
        composable(
            AppraiserContentRouting.Teachers.route,
            arguments = listOf(navArgument("monthIndex") {
                nullable = true
            })
        ){
            TeachersScreen()
        }
        composable(AppraiserContentRouting.Evaluation.route){

        }
        composable(AppraiserContentRouting.Criteria.route){

        }
    }
}