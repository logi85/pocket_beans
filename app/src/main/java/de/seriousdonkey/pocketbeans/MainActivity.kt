package de.seriousdonkey.pocketbeans

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var _navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _navController = findNavController(this, R.id.nav_host_fragment)
        _navController.addOnDestinationChangedListener(this)

        bottom_navigation_bar.setItemSelected(R.id.navigation_schedule)
        bottom_navigation_bar.setOnItemSelectedListener { id ->
            when(id) {
                R.id.navigation_schedule -> _navController.navigate(R.id.scheduleFragment)
                R.id.navigation_blog -> _navController.navigate(R.id.blogFragment)
                R.id.navigation_media_library -> _navController.navigate(R.id.mediaLibraryFragment)
                R.id.navigation_info -> _navController.navigate(R.id.infoFragment)
            }
        }
        createNotificationChannel()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val bottomNavigation = findViewById<ChipNavigationBar>(R.id.bottom_navigation_bar)
        if (hideBottomNavigation(destination.label.toString())) {
            bottomNavigation.visibility = View.GONE
        } else {
            bottomNavigation.visibility = View.VISIBLE
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "pocketbeans"
            val descriptionText = "pocketbeans"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("pocketbeans", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun hideBottomNavigation(label: String) : Boolean {
        return label == "fragment_schedule_details"
                || label == "fragment_show_info"
                || label == "fragment_media_library_info"
    }
}