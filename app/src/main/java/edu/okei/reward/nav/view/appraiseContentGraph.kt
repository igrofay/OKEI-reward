package edu.okei.reward.nav.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import edu.okei.reward.calculation_of_reward.view.CalculationOfRewardScreen
import edu.okei.reward.calendar_plan.view.CalendarPlanScreen
import edu.okei.reward.criteria.view.AddOrEditCriteriaScreen
import edu.okei.reward.criteria.view.CriteriaScreen
import edu.okei.reward.nav.model.AppraiserContentRouting
import edu.okei.reward.teachers.view.AddOrEditTeacherDialog
import edu.okei.reward.teachers.view.TeachersScreen


fun NavGraphBuilder.appraiseContentGraph(
    navController: NavController
) {
    navigation(
        startDestination = AppraiserContentRouting.CalendarPlan.route,
        route = AppraiserContentRouting.route
    ) {
        composable(AppraiserContentRouting.CalendarPlan.route) {
            CalendarPlanScreen(
                openCriteria = {
                    navController
                        .navigate(AppraiserContentRouting.Criteria.getRoute())
                },
                openTeachers = {
                    navController
                        .navigate(AppraiserContentRouting.Teachers.getRoute())
                },
                openTeachersInMonth = {monthIndex->
                    navController
                        .navigate(AppraiserContentRouting.Teachers.getRoute(monthIndex))
                },
                openCalculationOfReward ={
                    navController
                        .navigate(AppraiserContentRouting.CalculationOfReward.route)
                }
            )
        }
        composable(
            AppraiserContentRouting.Teachers.route,
            arguments = listOf(
                navArgument("monthIndex") {
                    nullable = true

                }
            )
        ) {
            TeachersScreen(
                addNewTeacher = {
                    navController.navigate(
                        AppraiserContentRouting.AddOrEditTeacher.route
                    )
                },
                openTeacherCriteria = { monthIndex, teacherId ->
                    navController
                        .navigate(AppraiserContentRouting.Criteria.getRoute(monthIndex, teacherId))
                }
            )
        }
        dialog(AppraiserContentRouting.AddOrEditTeacher.route) {
            AddOrEditTeacherDialog(
                onDismissRequest = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            AppraiserContentRouting.Criteria.route,
            arguments = listOf(
                navArgument("monthIndex") { nullable = true },
                navArgument("teacherId") { nullable = true }
            )
        ) {
            CriteriaScreen(
                addNewCriterion = {
                    navController.navigate(
                        AppraiserContentRouting.AddOrEditCriterion.getRoute()
                    )
                }
            )
        }
        composable(
            AppraiserContentRouting.AddOrEditCriterion.route,
            arguments = listOf(
                navArgument("idCriterion") { nullable = true }
            )
        ) {
            AddOrEditCriteriaScreen(
                onDismissRequest = {
                    navController.popBackStack()
                }
            )
        }
        composable(AppraiserContentRouting.CalculationOfReward.route){
            CalculationOfRewardScreen()
        }
    }
}