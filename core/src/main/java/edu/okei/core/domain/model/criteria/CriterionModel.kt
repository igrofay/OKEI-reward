package edu.okei.core.domain.model.criteria

interface CriterionModel {
    val id: String
    val name: String
    val serialNumber: String
    val description: String
    val evaluationOptions: List<EvaluationOptionModel>
    interface EvaluationOptionModel{
        val id: String
        val description:String
        val countPoints: Int
    }
}