package edu.okei.reward.criteria.model

import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.model.criteria.NewCriterionModel

class CriterionBuilder(
    private val listCriterion: List<CriterionModel>
) {
    private var name: String = ""
    private var description : String = ""
    private var serialNumber : String = ""
    private val evaluationOptions: MutableSet<NewCriterionModel.EvaluationOptionModel> = mutableSetOf()

    fun suggestDescriptions() : Set<String>{
        return listCriterion.map { it.description }.toSet()
    }
    fun suggestFirstSerialNumber() : Set<Int>{
        return listCriterion.map {
            it.serialNumber
                .split('.')
                .first()
                .toInt()
        }.toSet()
    }

    fun setName(value: String){ name = value}
    fun setDescription(value: String){ description = value}

    fun setSerialNumber(value: String) { serialNumber = value }
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

    fun setEvaluationOption(value: NewCriterionModel.EvaluationOptionModel){
        evaluationOptions.add(value)
    }
    fun removeEvaluationOption(value: NewCriterionModel.EvaluationOptionModel){
        evaluationOptions.remove(value)
    }

}