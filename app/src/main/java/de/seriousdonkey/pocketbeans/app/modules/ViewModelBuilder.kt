package de.seriousdonkey.pocketbeans.app.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import de.seriousdonkey.pocketbeans.app.viewmodel.DaggerAwareViewModelFactory

@Module
internal abstract class ViewModelBuilder {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerAwareViewModelFactory) : ViewModelProvider.Factory

}