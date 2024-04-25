package edu.okei.core.dependency_injection

import edu.okei.core.data.data_source.network.AuthApi
import edu.okei.core.data.repos.AuthReposImpl
import edu.okei.core.domain.repos.AuthRepos
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val ReposModule by DI.Module{
    bindProvider<AuthRepos> {
        AuthReposImpl(instance<AuthApi>())
    }
}