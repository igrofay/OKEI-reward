package edu.okei.reward.nav.view

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import edu.okei.reward.nav.model.StartRouting

@Composable
fun AppNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = StartRouting.route,
        modifier = Modifier.background(MaterialTheme.colors.background)
    ){
        startGraph(navController)
        appraiseContentGraph(navController)
    }
}