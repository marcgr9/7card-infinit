package ro.marc.sevencard.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.sevencard.R
import ro.marc.sevencard.databinding.FragmentHomeBinding
import ro.marc.sevencard.ui.MainActivity
import ro.marc.sevencard.ui.MainViewModel
import ro.marc.sevencard.ui.fragments.BaseFragment

class HomeFragment: BaseFragment<MainActivity, FragmentHomeBinding>() {

    private val vm: MainViewModel by sharedViewModel()
    private val navController by lazy {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.homeNavigation) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.bottomNavigation.setOnItemSelectedListener {
            if (it.itemId == binding.bottomNavigation.selectedItemId)
                return@setOnItemSelectedListener true

            when (it.itemId) {
                R.id.action_home -> {
                    navController.navigate(R.id.generateDestination)
                }
                R.id.action_decrypt -> {
                    navController.navigate(R.id.decryptDestination)
                }
                R.id.action_list -> {
                    navController.navigate(R.id.listDestination)
                }
            }
            return@setOnItemSelectedListener true
        }

        return binding.root
    }

    fun navigateToHome() {
        binding.bottomNavigation.selectedItemId = R.id.action_home
    }

}
