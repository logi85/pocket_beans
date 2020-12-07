package de.seriousdonkey.pocketbeans

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import de.seriousdonkey.pocketbeans.app.components.DaggerAppComponent

class PocketBeansApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}