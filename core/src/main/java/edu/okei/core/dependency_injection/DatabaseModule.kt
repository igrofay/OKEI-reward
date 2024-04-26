package edu.okei.core.dependency_injection

import android.content.Context
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private const val SharedPreferenceKey = "okei_reward"

internal val DatabaseModule by DI.Module {
    bindSingleton {
        instance<Context>().getSharedPreferences(
            SharedPreferenceKey,
            Context.MODE_PRIVATE
        )
    }
}