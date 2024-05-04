package edu.okei.reward.nav.model

import edu.okei.reward.common.model.AppRouting

sealed class StartRouting(route: String) : AppRouting(route) {
    data object Splash : StartRouting(
        "${Companion.route}_splash"
    )
    data object Auth : StartRouting(
        "${Companion.route}_auth"
    )
    companion object {
        const val route = "start_routing"
    }
}