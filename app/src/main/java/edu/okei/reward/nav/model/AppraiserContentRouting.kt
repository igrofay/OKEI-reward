package edu.okei.reward.nav.model

import edu.okei.reward.common.model.AppRouting

sealed class AppraiserContentRouting(route: String): AppRouting(route){
    data object CalendarPlan : AppraiserContentRouting("${route}_calder_plan")
    data object Teachers : AppraiserContentRouting("${route}_teachers?monthIndex={monthIndex}"){
        fun getRoute(monthIndex: Int? = null) =
            if(monthIndex != null) "${route}_teachers?monthIndex=$monthIndex"
            else "${route}_teachers"
    }
    data object Evaluation : AppraiserContentRouting("${route}_evaluation"){

    }
    data object Criteria : AppraiserContentRouting("${route}_criteria")
    companion object {
        const val route = "appraiser_content_routing"
    }
}