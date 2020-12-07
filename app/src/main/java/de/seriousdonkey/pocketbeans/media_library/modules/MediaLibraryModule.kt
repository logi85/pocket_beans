package de.seriousdonkey.pocketbeans.media_library.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import de.seriousdonkey.pocketbeans.app.viewmodel.ViewModelKey
import de.seriousdonkey.pocketbeans.media_library.ui.MediaLibraryFragment
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.last.LastEpisodesFragment
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.last.LastEpisodesViewModel
import de.seriousdonkey.pocketbeans.media_library.ui.show.info.ShowInfoFragment
import de.seriousdonkey.pocketbeans.media_library.ui.show.info.ShowInfoViewModel
import de.seriousdonkey.pocketbeans.media_library.ui.show.overview.ShowOverviewFragment
import de.seriousdonkey.pocketbeans.media_library.ui.show.overview.ShowOverviewViewModel

@Module
internal abstract class MediaLibraryModule {

    @ContributesAndroidInjector
    internal abstract fun showOverviewFragment() : ShowOverviewFragment

    @ContributesAndroidInjector
    internal abstract fun showInfoFragment() : ShowInfoFragment

    @ContributesAndroidInjector
    internal abstract fun lastEpisodesFragment() : LastEpisodesFragment

    @ContributesAndroidInjector
    internal abstract fun mediaLibraryFragment() : MediaLibraryFragment

    @Binds
    @IntoMap
    @ViewModelKey(ShowOverviewViewModel::class)
    abstract fun bindShowOverviewViewModel(viewModel: ShowOverviewViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShowInfoViewModel::class)
    abstract fun bindShowInfoViewModel(viewModel: ShowInfoViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LastEpisodesViewModel::class)
    abstract fun bindLastEpisodesViewModel(viewModel: LastEpisodesViewModel) : ViewModel
}