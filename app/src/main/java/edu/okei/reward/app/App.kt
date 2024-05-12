package edu.okei.reward.app

import android.app.Application
import edu.okei.core.dependency_injection.CoreModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindProvider

class App : Application(), DIAware {
    override val di by DI.lazy {
        bindProvider { applicationContext }
        bindProvider { applicationContext.contentResolver }
        import(CoreModule)
    }
}