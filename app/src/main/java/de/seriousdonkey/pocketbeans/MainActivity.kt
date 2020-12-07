package de.seriousdonkey.pocketbeans

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var _navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _navController = findNavController(this, R.id.nav_host_fragment)
        _navController.addOnDestinationChangedListener(this)

        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_schedule -> _navController.navigate(R.id.scheduleFragment)
                R.id.navigation_blog -> _navController.navigate(R.id.blogFragment)
                R.id.navigation_info -> _navController.navigate(R.id.infoFragment)
            }
            true
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }
}