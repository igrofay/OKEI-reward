package edu.okei.core.dependency_injection

import android.content.SharedPreferences
import edu.okei.core.data.data_source.network.AuthApi
import edu.okei.core.data.data_source.network.StatsApi
import edu.okei.core.data.repos.AuthReposImpl
import edu.okei.core.data.repos.CriteriaReposImpl
import edu.okei.core.data.repos.StatisticReposImpl
import edu.okei.core.data.repos.UsersManagementReposImpl
import edu.okei.core.data.repos.UserReposImpl
import edu.okei.core.domain.repos.AuthRepos
import edu.okei.core.domain.repos.CriteriaRepos
import edu.okei.core.domain.repos.StatisticRepos
import edu.okei.core.domain.repos.UserRepos
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.new

internal val ReposModule by DI.Module{
    bindProvider<AuthRepos> {
        AuthReposImpl(instance<AuthApi>())
    }
    bindSingleton<UserRepos>{
        UserReposImpl(instance<SharedPreferences>())
    }
    bindProvider<StatisticRepos>{
        StatisticReposImpl(instance<StatsApi>())
    }
    bindProvider {
        new(::UsersManagementReposImpl)
    }
    bindProvider<CriteriaRepos> {
        new(::CriteriaReposImpl)
    }
}