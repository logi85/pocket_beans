package de.seriousdonkey.pocketbeans.info.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import de.seriousdonkey.pocketbeans.app.viewmodel.ViewModelKey
import de.seriousdonkey.pocketbeans.info.ui.InfoFragment
import de.seriousdonkey.pocketbeans.info.ui.viewmodel.InfoListViewModel

@Module
internal abstract class InfoUiModule {
    @ContributesAndroidInjector
    internal abstract fun infoFragment() : InfoFragment

    @Binds
    @IntoMap
    @ViewModelKey(InfoListViewModel::class)
    abstract fun bindInfoListViewModel(viewModel: InfoListViewModel) : ViewModel
}