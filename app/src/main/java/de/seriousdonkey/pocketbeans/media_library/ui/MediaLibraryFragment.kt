package de.seriousdonkey.pocketbeans.media_library.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import dagger.android.support.DaggerFragment
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.media_library.ui.episodes.last.LastEpisodesFragment
import de.seriousdonkey.pocketbeans.media_library.ui.show.overview.ShowOverviewFragment
import kotlinx.android.synthetic.main.fragment_media_library.*

class MediaLibraryFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_media_library, container, false)
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(childFragmentManager, requireContext())
        adapter.addFragment(LastEpisodesFragment.newInstance())
        adapter.addFragment(ShowOverviewFragment.newInstance())

        mediaLibraryViewPager.adapter = adapter
        mediaLibraryTabLayout.setupWithViewPager(mediaLibraryViewPager)
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager, context: Context) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val _fragments = mutableListOf<Fragment>()
        private val _fragmentTitles = listOf(
                context.resources.getString(R.string.media_library_tab_last_episodes),
                context.resources.getString(R.string.media_library_all_shows)
        )

        override fun getCount() = _fragments.size
        override fun getItem(position: Int) = _fragments[position]

        override fun getPageTitle(position: Int) = _fragmentTitles[position]

        fun addFragment(fragment: Fragment) {
            _fragments.add(fragment)
        }

    }

}