package edu.okei.core.dependency_injection

import org.kodein.di.DI

val CoreModule by DI.Module{
    import(ReposModule)
    import(NetworkModule)
}