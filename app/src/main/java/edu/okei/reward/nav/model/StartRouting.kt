package edu.okei.reward.nav.model

import edu.okei.reward.common.model.AppRouting

sealed class StartRouting(route:String) : AppRouting(route) {
    data object Splash : StartRouting("${route}_splash")
    data object Auth : StartRouting("${route}_auth")
    companion object {
        const val route = "start_routing"
    }
}