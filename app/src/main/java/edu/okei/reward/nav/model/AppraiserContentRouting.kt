package edu.okei.reward.nav.model

import edu.okei.reward.common.model.AppRouting

sealed class AppraiserContentRouting(route: String): AppRouting(route){
    data object CalendarPlan : AppraiserContentRouting("${route}_calder_plan")
    data object Teachers : AppraiserContentRouting("${route}_teachers")
    data object Evaluation : AppraiserContentRouting("${route}_evaluation")
    companion object {
        const val route = "appraiser_content_routing"
    }
}