package edu.okei.reward.app

import android.app.Application
import edu.okei.core.dependency_injection.CoreModule
import org.kodein.di.DI
import org.kodein.di.DIAware

class App : Application(), DIAware {
    override val di by DI.lazy {
        import(CoreModule)
    }
}