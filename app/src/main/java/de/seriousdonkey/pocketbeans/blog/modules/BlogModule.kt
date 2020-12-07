package de.seriousdonkey.pocketbeans.blog.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import de.seriousdonkey.pocketbeans.app.viewmodel.ViewModelKey
import de.seriousdonkey.pocketbeans.blog.ui.BlogFragment
import de.seriousdonkey.pocketbeans.blog.ui.viewmodels.BlogViewModel

@Module
internal abstract class BlogModule {
    @ContributesAndroidInjector
    internal abstract fun blogFragment() : BlogFragment

    @Binds
    @IntoMap
    @ViewModelKey(BlogViewModel::class)
    abstract fun bindBlogViewModel(viewModel: BlogViewModel) : ViewModel

}