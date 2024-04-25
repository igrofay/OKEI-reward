package edu.okei.core.domain.model.errors

abstract class AppErrors : Throwable(){
    data class UnhandledError(override val message: String?) : AppErrors()
}