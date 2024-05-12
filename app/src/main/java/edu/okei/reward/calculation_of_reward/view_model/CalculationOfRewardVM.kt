package edu.okei.reward.calculation_of_reward.view_model

import android.net.Uri
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.model.errors.ReportError
import edu.okei.core.domain.repos.StatisticRepos
import edu.okei.core.domain.use_case.teacher.GetListTeacherRatingUseCase
import edu.okei.reward.R
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardEvent
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardSideEffect
import edu.okei.reward.calculation_of_reward.model.CalculationOfRewardState
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

class CalculationOfRewardVM(
    override val di: DI
) : AppVM<CalculationOfRewardState, CalculationOfRewardSideEffect, CalculationOfRewardEvent>(),
    DIAware {
    private val getListTeacherRating by di.instance<GetListTeacherRatingUseCase>()
    private val statisticRepos by di.instance<StatisticRepos>()

    override val container: Container<CalculationOfRewardState, CalculationOfRewardSideEffect> =
        viewModelScope.container(CalculationOfRewardState.Load) {
            loadMonthlyReward()
        }

    private fun loadMonthlyReward() = intent {
        statisticRepos.getMonthlyProgress()
            .onFailure(::onError)
            .onSuccess { model ->
                reduce {
                    CalculationOfRewardState.ListMonthsOfReward(model)
                }
            }
    }

    override fun onEvent(event: CalculationOfRewardEvent) {
        when (event) {
            is CalculationOfRewardEvent.SeeReportForMonth -> loadReportForMonth(event.monthIndex)
            is CalculationOfRewardEvent.InputFixedPremium -> inputFixedPremium(event.fixedPremium)
            is CalculationOfRewardEvent.InputPartSemiannualPremium -> inputPartSemiannualPremium(
                event.partSemiannualPremium
            )

            is CalculationOfRewardEvent.InputTotalAmountPremium -> inputTotalAmountPremium(event.totalAmountPremium)
            CalculationOfRewardEvent.CreateReportData -> createReportData()
            CalculationOfRewardEvent.SeeReportData -> blockingIntent {
                postSideEffect(CalculationOfRewardSideEffect.OpenReportData)
            }

            is CalculationOfRewardEvent.DownloadReport -> downloadReport(event.uri)
        }
    }

    private fun downloadReport(uri: Uri) = intent {
        val url = (state as? CalculationOfRewardState.RewardReportForMonth)?.report?.urlReport
            ?: return@intent
        statisticRepos.downloadFile(uri, url)
            .onFailure(::onError)
            .onSuccess {
                if (it){
                    postSideEffect(CalculationOfRewardSideEffect.ShowMessage(R.string.file_has_been_saved))
                }
            }
    }

    private fun loadReportForMonth(monthIndex: Int) = intent {
        statisticRepos.getReportForMonth(monthIndex)
            .onFailure(::onError)
            .onSuccess { model ->
                reduce {
                    CalculationOfRewardState.RewardReportForMonth(model, monthIndex)
                }
            }
    }

    override fun onError(er: Throwable) {
        when (er) {
            is ReportError.ReportNotFound -> checkIfReportCanBeCreated(er.monthIndex)
            else -> Log.e(nameVM, er.message, er)
        }
    }

    private fun checkIfReportCanBeCreated(monthIndex: Int) = intent {
        if (
            (state as CalculationOfRewardState.ListMonthsOfReward)
                .monthlyProgress.currentMonth.monthIndex == monthIndex
        ) {
            getListTeacherRating.execute(monthIndex)
                .onFailure(::onError)
                .onSuccess { list ->
                    reduce {
                        CalculationOfRewardState.CreateReportForMonth(
                            monthIndex = monthIndex,
                            totalAmountPoints = list.sumOf { it.sumMarks },
                            countTeacher = list.size
                        )
                    }
                }
        } else {
            postSideEffect(CalculationOfRewardSideEffect.ShowMessage(R.string.report_does_not_exist))
        }
    }

    private fun inputFixedPremium(fixedPremium: String) = blockingIntent {
        val targetState =
            state as? CalculationOfRewardState.CreateReportForMonth ?: return@blockingIntent
        if (fixedPremium.isBlank())
            reduce {
                targetState.copy(
                    fixedPremium = 0
                )
            }
        else if (fixedPremium.isDigitsOnly())
            reduce {
                targetState.copy(
                    fixedPremium = fixedPremium.toInt()
                )
            }
        calculateReportData()
    }

    private fun inputPartSemiannualPremium(partSemiannualPremium: String) = blockingIntent {
        val targetState =
            state as? CalculationOfRewardState.CreateReportForMonth ?: return@blockingIntent
        if (partSemiannualPremium.isBlank())
            reduce {
                targetState.copy(
                    partSemiannualPremium = 0
                )
            }
        else if (partSemiannualPremium.isDigitsOnly())
            reduce {
                targetState.copy(
                    partSemiannualPremium = partSemiannualPremium.toInt()
                )
            }
        calculateReportData()
    }

    private fun inputTotalAmountPremium(totalAmountPremium: String) = blockingIntent {
        val targetState =
            state as? CalculationOfRewardState.CreateReportForMonth ?: return@blockingIntent
        if (totalAmountPremium.isBlank())
            reduce {
                targetState.copy(
                    totalAmountPremium = 0
                )
            }
        else if (totalAmountPremium.isDigitsOnly())
            reduce {
                targetState.copy(
                    totalAmountPremium = totalAmountPremium.toInt()
                )
            }
        calculateReportData()
    }

    private fun calculateReportData() = intent {
        val targetState =
            state as? CalculationOfRewardState.CreateReportForMonth ?: return@intent
        val fixedPremiumForTeachers = targetState.countTeacher * targetState.fixedPremium
        val distributablePremium =
            (targetState.totalAmountPremium - targetState.partSemiannualPremium) - fixedPremiumForTeachers
        val costByPoint = if (targetState.totalAmountPoints != 0)
            distributablePremium / targetState.totalAmountPoints
        else
            0
        reduce {
            targetState.copy(
                distributablePremium = distributablePremium,
                costByPoint = costByPoint,
                fixedPremiumForTeachers = fixedPremiumForTeachers
            )
        }
    }

    private fun createReportData() = intent {
        val targetState =
            state as? CalculationOfRewardState.CreateReportForMonth ?: return@intent
        if (targetState.distributablePremium < 0) return@intent postSideEffect(
            CalculationOfRewardSideEffect.ShowMessage(R.string.fields_not_filled_in_correctly)
        )
        statisticRepos
            .createReportForMonth(targetState.monthIndex, targetState)
            .onFailure(::onError)
            .onSuccess { model ->
                reduce {
                    CalculationOfRewardState
                        .RewardReportForMonth(model, targetState.monthIndex)
                }
            }
    }
}