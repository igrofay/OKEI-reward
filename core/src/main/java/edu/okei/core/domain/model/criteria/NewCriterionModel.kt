package edu.okei.core.domain.model.criteria

interface NewCriterionModel {
    val name:String
    val description : String
    val serialNumber : String
    val evaluationOptions: List<EvaluationOptionModel>
    interface EvaluationOptionModel{
        val description:String
        val countPoints: Int
    }
}