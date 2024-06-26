package edu.okei.reward.criteria.model

import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.model.criteria.NewCriterionModel

class CriterionBuilder(
    private val listDescription: List<String>
) {
    private var name: String = ""
    private var description: String = ""
    private var serialNumber: String = ""
    private val evaluationOptions: MutableList<NewCriterionModel.EvaluationOptionModel> = mutableListOf()

    fun suggestDescriptions(filter: String = ""): List<String> {
        return listDescription.toSet()
            .filter {
                if (filter.isBlank()) true
                else it.contains(filter, ignoreCase = true)
            }
    }


    fun setName(value: String) {
        name = value
    }

    fun setDescription(value: String) {
        description = value
    }

    fun setSerialNumber(value: String) {
        serialNumber = value
    }

    fun isSerialNumber(value: String): Boolean {
        if (value.startsWith(".")) {
            return false
        }
        val parts = value.split(".")
        for (i in parts.indices) {
            if (i == parts.lastIndex && parts[i].isEmpty()) continue
            if (!parts[i].matches("\\d+".toRegex())) {
                return false
            }
        }
        return true
    }

    fun setNewEvaluationOption(value: NewCriterionModel.EvaluationOptionModel) {
        evaluationOptions.add(value)
    }
    fun updateEvaluationOption(index: Int, value: NewCriterionModel.EvaluationOptionModel){
        evaluationOptions[index] = value
    }
    fun removeEvaluationOption(index: Int) {
        evaluationOptions.removeAt(index)
    }

    fun build() = object : NewCriterionModel{
        override val name: String = this@CriterionBuilder.name.trim()
        override val description: String = this@CriterionBuilder.description.trim()
        override val serialNumber: String = this@CriterionBuilder.serialNumber.trim('.')
        override val evaluationOptions: List<NewCriterionModel.EvaluationOptionModel> =
            this@CriterionBuilder.evaluationOptions.map {
                object : NewCriterionModel.EvaluationOptionModel{
                    override val description: String = it.description.trim()
                    override val countPoints: Int = it.countPoints
                }
            }
    }
}