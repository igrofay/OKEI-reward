package edu.okei.reward.nav.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import edu.okei.reward.auth.view.AuthScreen
import edu.okei.reward.nav.model.AppraiserContentRouting
import edu.okei.reward.nav.model.StartRouting
import edu.okei.reward.splash.view.SplashScreen

fun NavGraphBuilder.startGraph(
    navController: NavController,
){
    navigation(
        startDestination = StartRouting.Splash.route,
        route = StartRouting.route,
    ){
        composable(StartRouting.Splash.route){
            SplashScreen(
                openAuth = {
                    navController.navigate(StartRouting.Auth.route){
                        popUpTo(StartRouting.Splash.route){
                            inclusive = true
                        }
                    }
                },
                openAppraiserContent = {
                    navController.navigate(AppraiserContentRouting.route){
                        popUpTo(StartRouting.Splash.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(StartRouting.Auth.route){
            AuthScreen(
                openAppraiserContent = {
                    navController.navigate(AppraiserContentRouting.route){
                        popUpTo(StartRouting.Auth.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}