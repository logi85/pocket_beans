package de.seriousdonkey.pocketbeans.app.components

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.seriousdonkey.pocketbeans.PocketBeansApplication
import de.seriousdonkey.pocketbeans.app.modules.AppModule
import de.seriousdonkey.pocketbeans.app.modules.ViewModelBuilder
import de.seriousdonkey.pocketbeans.blog.modules.BlogApiModule
import de.seriousdonkey.pocketbeans.blog.modules.BlogModule
import de.seriousdonkey.pocketbeans.schedule.modules.ScheduleApiModule
import de.seriousdonkey.pocketbeans.schedule.modules.ScheduleModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ViewModelBuilder::class,

    ScheduleModule::class,
    ScheduleApiModule::class,

    BlogModule::class,
    BlogApiModule::class
])
interface AppComponent : AndroidInjector<PocketBeansApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<PocketBeansApplication>

}