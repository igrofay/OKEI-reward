package edu.okei.reward.calendar_plan.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import edu.okei.core.domain.repos.StatisticRepos
import edu.okei.reward.calendar_plan.model.CalendarPlanEvent
import edu.okei.reward.calendar_plan.model.CalendarPlanSideEffect
import edu.okei.reward.calendar_plan.model.CalendarPlanState
import edu.okei.reward.common.view_model.AppVM
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class CalendarPlanVM(
    override val di: DI
) :
    AppVM<CalendarPlanState, CalendarPlanSideEffect, CalendarPlanEvent>(),
    DIAware
{
    private val statisticRepos by di.instance<StatisticRepos>()

    override val container: Container<CalendarPlanState, CalendarPlanSideEffect> =
        viewModelScope.container(CalendarPlanState.Load){
            loadMonthProgress()
        }
    private fun loadMonthProgress() = intent {
        statisticRepos.getMonthlyProgress().fold(
            onSuccess = { model: MonthlyProgressModel ->
                reduce {
                    CalendarPlanState.MonthProgress(model)
                }
            },
            onFailure = ::onError
        )
    }
    override fun onError(er: Throwable) {
        Log.e(nameVM, er.message, er)
    }

    override fun onEvent(event: CalendarPlanEvent) {
        when(event){
            CalendarPlanEvent.SeeCriteria -> blockingIntent {
                postSideEffect(CalendarPlanSideEffect.OpenCriteria)
            }
            CalendarPlanEvent.SeeTeacher -> blockingIntent {
                postSideEffect(CalendarPlanSideEffect.OpenTeachers)
            }
            is CalendarPlanEvent.SeeTeachersInMonth -> blockingIntent {
                postSideEffect(CalendarPlanSideEffect.OpenTeachersInMonth(event.mothIndex))
            }
        }
    }
}