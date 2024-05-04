package edu.okei.reward.nav.model

import edu.okei.reward.common.model.AppRouting

sealed class AppraiserContentRouting(route: String) : AppRouting(route) {
    data object CalendarPlan : AppraiserContentRouting(
        "${Companion.route}_calder_plan"
    )
    data object AddOrEditTeacher : AppraiserContentRouting(
        "${Companion.route}_add_or_edit_teacher"
    )
    data object Teachers : AppraiserContentRouting(
            "${Companion.route}_teachers?monthIndex={monthIndex}"
    ) {
        fun getRoute(monthIndex: Int? = null) =
            if (monthIndex != null) "${Companion.route}_teachers?monthIndex=$monthIndex"
            else "${Companion.route}_teachers"
    }

    data object Criteria : AppraiserContentRouting(
        "${Companion.route}_criteria?monthIndex={monthIndex}&teacherId={teacherId}"
    ) {
        fun getRoute(monthIndex: Int? = null, teacherId: String? = null) =
            if (monthIndex != null && teacherId != null) "${Companion.route}_criteria?monthIndex=${monthIndex}&teacherId=${teacherId}"
            else "${Companion.route}_criteria"
    }

    data object AddOrEditCriterion : AppraiserContentRouting(
        "${Companion.route}_add_or_edit_criterion?idCriterion={idCriterion}"
    ){
        fun getRoute(idCriterion: String? = null) =
            if (idCriterion != null) "${Companion.route}_add_or_edit_criterion?idCriterion=${idCriterion}"
            else "${Companion.route}_add_or_edit_criterion"
    }

    companion object {
        const val route = "appraiser_content_routing"
    }
}