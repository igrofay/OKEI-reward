package edu.okei.core.domain.model.errors

sealed class ReportError : AppErrors() {
    data class ReportNotFound(val monthIndex: Int) : ReportError()
}