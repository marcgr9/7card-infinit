package ro.marc.sevencard.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.sevencard.databinding.FragmentHomeGenerateBinding
import ro.marc.sevencard.ui.MainActivity
import ro.marc.sevencard.ui.MainViewModel
import ro.marc.sevencard.ui.fragments.BaseFragment

class HomeGenerateFragment: BaseFragment<MainActivity, FragmentHomeGenerateBinding>() {

    private val vm: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        binding.generateButton.setOnClickListener {
            if (!vm.userId.value.isNullOrBlank()) {
                vm.navigateTo(MainViewModel.NavigationCase.Qr)
            }
        }

        return binding.root
    }

}
