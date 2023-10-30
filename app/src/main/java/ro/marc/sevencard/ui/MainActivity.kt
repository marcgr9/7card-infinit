package ro.marc.sevencard.ui

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ro.marc.sevencard.R

class MainActivity: FragmentActivity() {

    private val vm: MainViewModel by viewModel()

    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.primaryNavigation) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.navigationEvent.observe(this) {
            when (it) {
                MainViewModel.NavigationCase.Home -> {
                    navController.navigate(R.id.homeDestination)
                }
                MainViewModel.NavigationCase.Qr -> {
                    navController.navigate(R.id.qrDestination)
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

}
