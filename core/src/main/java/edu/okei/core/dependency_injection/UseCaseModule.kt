package edu.okei.core.dependency_injection

import edu.okei.core.domain.use_case.AuthUseCase
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.new

internal val UseCaseModule by DI.Module{
    bindProvider {
        new(::AuthUseCase)
    }
}