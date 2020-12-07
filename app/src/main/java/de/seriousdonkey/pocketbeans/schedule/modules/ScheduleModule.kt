package de.seriousdonkey.pocketbeans.schedule.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import de.seriousdonkey.pocketbeans.app.viewmodel.ViewModelKey
import de.seriousdonkey.pocketbeans.schedule.ui.details.ScheduleDetailsFragment
import de.seriousdonkey.pocketbeans.schedule.ui.overview.ScheduleFragment
import de.seriousdonkey.pocketbeans.schedule.ui.overview.viewmodels.ScheduleViewModel

@Module
internal abstract class ScheduleModule {

    @ContributesAndroidInjector
    internal abstract fun scheduleFragment() : ScheduleFragment

    @ContributesAndroidInjector
    internal abstract fun scheduleDetailsFragment() : ScheduleDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindScheduleViewModel(viewModel: ScheduleViewModel) : ViewModel


}