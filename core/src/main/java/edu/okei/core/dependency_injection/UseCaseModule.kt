package edu.okei.core.dependency_injection

import edu.okei.core.domain.use_case.teacher.AddTeacherUseCase
import edu.okei.core.domain.use_case.auth.AuthUseCase
import edu.okei.core.domain.use_case.criterion.CreateCriterionUseCase
import edu.okei.core.domain.use_case.criterion.DeleteCriterionUseCase
import edu.okei.core.domain.use_case.teacher.DeleteTeacherUseCase
import edu.okei.core.domain.use_case.criterion.GetCriteriaUseCase
import edu.okei.core.domain.use_case.teacher.GetListTeacherRatingUseCase
import edu.okei.core.domain.use_case.auth.RestartSessionUseCase
import edu.okei.core.domain.use_case.criterion.GetCriteriaForEvaluatingTeacher
import edu.okei.core.domain.use_case.teacher.CreateTeacherEvaluationUseCase
import edu.okei.core.domain.use_case.teacher.GetTeacherMonthEvaluations
import edu.okei.core.domain.use_case.teacher.GetTeachersUseCase
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.new

internal val UseCaseModule by DI.Module {
    bindProvider { new(::AuthUseCase) }
    bindProvider { new(::RestartSessionUseCase) }
    bindProvider { new(::GetTeachersUseCase) }
    bindProvider { new(::DeleteCriterionUseCase) }
    bindProvider { new(::GetListTeacherRatingUseCase) }
    bindProvider { new(::CreateCriterionUseCase) }
    bindProvider { new(::GetCriteriaUseCase) }
    bindProvider { new(::AddTeacherUseCase) }
    bindProvider { new(::DeleteTeacherUseCase) }
    bindProvider { new(::GetTeacherMonthEvaluations) }
    bindProvider { new(::GetCriteriaForEvaluatingTeacher) }
    bindProvider { new(::CreateTeacherEvaluationUseCase) }
}