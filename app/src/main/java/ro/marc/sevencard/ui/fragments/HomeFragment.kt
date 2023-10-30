package ro.marc.sevencard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.sevencard.databinding.FragmentHomeBinding
import ro.marc.sevencard.ui.MainActivity
import ro.marc.sevencard.ui.MainViewModel

class HomeFragment: BaseFragment<MainActivity, FragmentHomeBinding>() {

    private val vm: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        binding.generateButton.setOnClickListener {
            vm.navigateTo(MainViewModel.NavigationCase.Qr)
        }

        return binding.root
    }

}
