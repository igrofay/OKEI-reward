package edu.okei.core.dependency_injection

import edu.okei.core.domain.use_case.AuthUseCase
import edu.okei.core.domain.use_case.CriteriaIterator
import edu.okei.core.domain.use_case.RestartSessionUseCase
import edu.okei.core.domain.use_case.TeachersIterator
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.new

internal val UseCaseModule by DI.Module{
    bindProvider {
        new(::AuthUseCase)
    }
    bindProvider {
        new(::RestartSessionUseCase)
    }
    bindProvider {
        new(::TeachersIterator)
    }
    bindProvider {
        new(::CriteriaIterator)
    }
}