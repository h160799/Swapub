package com.johnny.swapub

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.johnny.swapub.databinding.ActivityMainBinding
import com.johnny.swapub.util.CurrentFragmentType
import com.johnny.swapub.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.Observer

class MainActivity : AppCompatActivity() {



    /**
     * Lazily initialize our [MainViewModel].
     */
    lateinit var viewModel: MainViewModel

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private lateinit var binding: ActivityMainBinding
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT // calculateStatusColor(Color.WHITE, (int) alphaValue)


        setupBottomNav()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_messageHistory -> {

                findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_messageHistoryFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_wishNews -> {

                findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_wishNewsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {

                findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_profileFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }







    private fun setupBottomNav() {
        binding.bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val menuView = binding.bottomNavView.getChildAt(0) as BottomNavigationMenuView
        val itemView = menuView.getChildAt(2) as BottomNavigationItemView
    }

    private fun setupNavController() {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.homeFragment -> CurrentFragmentType.HOME
                R.id.messageHistoryFragment -> CurrentFragmentType.MESSAGEHISTORY
                R.id.wishNewsFragment -> CurrentFragmentType.WISHNEWS
                R.id.profileFragment -> CurrentFragmentType.PROFILE
                else -> viewModel.currentFragmentType.value
            }
        }
    }





}

