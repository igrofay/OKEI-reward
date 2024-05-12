package edu.okei.core.domain.model.statistics

interface CreateReportDataModel {
    val totalAmountPremium: Int // Общая сумма премий
    val fixedPremium: Int // Фиксированная премия
    val totalAmountPoints: Int // Общее кол-во баллов
    val partSemiannualPremium: Int //часть полугодовой премии
    val distributablePremium: Int // распределяемая премия
    val costByPoint: Int // Стоимость балла
}