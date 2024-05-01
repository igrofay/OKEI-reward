package edu.okei.core.dependency_injection

import android.content.SharedPreferences
import edu.okei.core.data.data_source.network.AuthApi
import edu.okei.core.data.data_source.network.StatsApi
import edu.okei.core.data.data_source.network.TeacherApi
import edu.okei.core.data.repos.AuthReposImpl
import edu.okei.core.data.repos.StatisticReposImpl
import edu.okei.core.data.repos.TeachersReposImpl
import edu.okei.core.data.repos.UserReposImpl
import edu.okei.core.domain.repos.AuthRepos
import edu.okei.core.domain.repos.StatisticRepos
import edu.okei.core.domain.repos.UserRepos
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import java.util.Stack

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
        TeachersReposImpl(instance<TeacherApi>())
    }
}