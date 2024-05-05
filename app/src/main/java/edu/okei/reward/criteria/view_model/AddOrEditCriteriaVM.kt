package edu.okei.reward.criteria.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.use_case.CriteriaIterator
import edu.okei.reward.R
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.criteria.model.AddOrEditCriteriaEvent
import edu.okei.reward.criteria.model.AddOrEditCriteriaSideEffect
import edu.okei.reward.criteria.model.AddOrEditCriteriaState
import edu.okei.reward.criteria.model.CriteriaEvent
import edu.okei.reward.criteria.model.CriteriaEventTransmitter
import edu.okei.reward.criteria.model.CriterionBuilder
import edu.okei.reward.criteria.model.NewEvaluationOptionData
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class AddOrEditCriteriaVM(
    override val di: DI
) : AppVM<AddOrEditCriteriaState, AddOrEditCriteriaSideEffect, AddOrEditCriteriaEvent>(),
    DIAware {
    private val criteriaIterator by di.instance<CriteriaIterator>()
    private lateinit var builder: CriterionBuilder
    override val container: Container<AddOrEditCriteriaState, AddOrEditCriteriaSideEffect> =
        viewModelScope.container(AddOrEditCriteriaState.Load) {
            loadListCriterion()
        }

    override fun onEvent(event: AddOrEditCriteriaEvent) {
        when (event) {
            is AddOrEditCriteriaEvent.InputName -> inputName(event.name)
            is AddOrEditCriteriaEvent.InputDescription -> inputDescription(event.description)
            is AddOrEditCriteriaEvent.InputSerialNumber -> inputSerialNumber(event.serialNumber)
            AddOrEditCriteriaEvent.AddEvaluationOption -> addNewEvaluationOption()
            is AddOrEditCriteriaEvent.DeleteEvaluationOption -> deleteEvaluationOption(event.index)
            is AddOrEditCriteriaEvent.InputDescriptionEvaluationOption ->
                inputDescriptionEvaluationOption(event.index, event.description)

            is AddOrEditCriteriaEvent.DecrementCountPointersInEvaluationOption ->
                decrementCountPointersInEvaluationOption(event.index)

            is AddOrEditCriteriaEvent.IncrementCountPointersInEvaluationOption ->
                incrementCountPointersInEvaluationOption(event.index)
            AddOrEditCriteriaEvent.AddOrEditCriterion -> addOrEditCriterion()
        }
    }

    override fun onError(er: Throwable) {
        Log.e(nameVM, er.message, er)
    }

    private fun loadListCriterion() = intent {
        criteriaIterator.getCriteria()
            .onFailure(::onError)
            .onSuccess { list ->
                builder = CriterionBuilder(list)
                reduce {
                    AddOrEditCriteriaState.AddOrEditCriteria(
                        examplesOfDescriptions = builder.suggestDescriptions(),
                    )
                }
            }
    }

    private fun inputName(name: String) = blockingIntent {
        builder.setName(name)
        reduce {
            (state as AddOrEditCriteriaState.AddOrEditCriteria).copy(
                nameCriterion = name
            )
        }
    }

    private fun inputDescription(description: String) = blockingIntent {
        builder.setDescription(description)
        reduce {
            (state as AddOrEditCriteriaState.AddOrEditCriteria).copy(
                description = description,
                examplesOfDescriptions = builder.suggestDescriptions(description)
            )
        }
    }

    private fun inputSerialNumber(serialNumber: String) = blockingIntent {
        if (!builder.isSerialNumber(serialNumber)) return@blockingIntent
        builder.setSerialNumber(serialNumber)
        reduce {
            (state as AddOrEditCriteriaState.AddOrEditCriteria).copy(
                serialNumber = serialNumber
            )
        }
    }

    private fun addNewEvaluationOption() = intent {
        NewEvaluationOptionData().let { newEvaluationOption ->
            builder.setNewEvaluationOption(newEvaluationOption)
            reduce {
                (state as AddOrEditCriteriaState.AddOrEditCriteria).run {
                    this.copy(
                        evaluationOptions = this
                            .evaluationOptions
                            .toMutableList()
                            .apply {
                                add(newEvaluationOption)
                            }
                    )
                }
            }
        }
    }

    private fun deleteEvaluationOption(index: Int) = intent {
        builder.removeEvaluationOption(index)
        reduce {
            (state as AddOrEditCriteriaState.AddOrEditCriteria).run {
                this.copy(
                    evaluationOptions = this
                        .evaluationOptions
                        .toMutableList()
                        .apply {
                            removeAt(index)
                        }
                )
            }
        }
    }

    private fun inputDescriptionEvaluationOption(index: Int, description: String) = blockingIntent {
        val evaluationOptionData = (state as AddOrEditCriteriaState.AddOrEditCriteria)
            .evaluationOptions[index].copy(
            description = description
        )
        builder.updateEvaluationOption(index, evaluationOptionData)
        reduce {
            (state as AddOrEditCriteriaState.AddOrEditCriteria).run {
                this.copy(
                    evaluationOptions = this
                        .evaluationOptions
                        .toMutableList()
                        .apply {
                            this[index] = evaluationOptionData
                        }
                )
            }
        }
    }

    private fun incrementCountPointersInEvaluationOption(index: Int) = blockingIntent {
        val evaluationOptionData = (state as AddOrEditCriteriaState.AddOrEditCriteria)
            .evaluationOptions[index]
            .let {
                if (it.countPoints + 1 > 100) return@blockingIntent
                it.copy(
                    countPoints = it.countPoints + 1
                )
            }
        builder.updateEvaluationOption(index, evaluationOptionData)
        reduce {
            (state as AddOrEditCriteriaState.AddOrEditCriteria).run {
                this.copy(
                    evaluationOptions = this
                        .evaluationOptions
                        .toMutableList()
                        .apply {
                            this[index] = evaluationOptionData
                        }
                )
            }
        }
    }

    private fun decrementCountPointersInEvaluationOption(index: Int) = blockingIntent {
        val evaluationOptionData = (state as AddOrEditCriteriaState.AddOrEditCriteria)
            .evaluationOptions[index]
            .let {
                if (it.countPoints - 1 < 0) return@blockingIntent
                it.copy(
                    countPoints = it.countPoints - 1
                )
            }
        builder.updateEvaluationOption(index, evaluationOptionData)
        reduce {
            (state as AddOrEditCriteriaState.AddOrEditCriteria).run {
                this.copy(
                    evaluationOptions = this
                        .evaluationOptions
                        .toMutableList()
                        .apply {
                            this[index] = evaluationOptionData
                        }
                )
            }
        }
    }
    private fun addOrEditCriterion() = intent {
        val addOrEditCriteria = state as AddOrEditCriteriaState.AddOrEditCriteria
        if(addOrEditCriteria.nameCriterion.isBlank())
            return@intent postSideEffect(AddOrEditCriteriaSideEffect.ShowMessage(R.string.fill_in_blanks))
        if (addOrEditCriteria.description.isBlank())
            return@intent postSideEffect(AddOrEditCriteriaSideEffect.ShowMessage(R.string.fill_in_blanks))
        if (addOrEditCriteria.serialNumber.isBlank())
            return@intent postSideEffect(AddOrEditCriteriaSideEffect.ShowMessage(R.string.fill_in_blanks))
        for(item in addOrEditCriteria.evaluationOptions){
            if (item.description.isBlank()) return@intent postSideEffect(AddOrEditCriteriaSideEffect.ShowMessage(R.string.fill_in_blanks))
        }
        criteriaIterator.createCriterion(builder.build())
            .onFailure(::onError)
            .onSuccess {
                CriteriaEventTransmitter.sendMessage(CriteriaEvent.UpdateListCriterion)
                postSideEffect(AddOrEditCriteriaSideEffect.EditsCompleted)
            }
    }
}