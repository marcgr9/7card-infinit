package ro.marc.sevencard.ui.fragments.decrypt

import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ro.marc.sevencard.R
import ro.marc.sevencard.databinding.FragmentDecryptBinding
import ro.marc.sevencard.ui.MainActivity
import ro.marc.sevencard.ui.fragments.BaseFragment

class DecryptFragment: BaseFragment<MainActivity, FragmentDecryptBinding>() {

    private val decryptVm: DecryptViewModel by lazy {
        this@DecryptFragment.getViewModel()
    }

    private val navController by lazy {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.decryptNavigation) as NavHostFragment
        navHostFragment.navController
    }

    fun goToCamera() {
        navController.navigate(R.id.decryptScannerDestination)
    }

    fun goToInput(hasResult: Boolean) {
        navController.navigate(R.id.decryptInputDestination, bundleOf("hasResult" to hasResult))
    }

}
