package ro.marc.sevencard.ui.fragments.decrypt

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.sevencard.databinding.FragmentDecryptBinding
import ro.marc.sevencard.databinding.FragmentDecryptInputBinding
import ro.marc.sevencard.ui.MainActivity
import ro.marc.sevencard.ui.MainViewModel
import ro.marc.sevencard.ui.fragments.BaseFragment
import ro.marc.sevencard.ui.fragments.home.HomeFragment

class DecryptInputFragment: BaseFragment<MainActivity, FragmentDecryptInputBinding>() {

    private val vm: MainViewModel by sharedViewModel()
    private val decryptVm: DecryptViewModel by lazy {
        parent.getViewModel()
    }

    private val homeParent: HomeFragment by lazy {
        parentFragment!!.parentFragment!!.parentFragment!!.parentFragment as HomeFragment
    }

    private val parent: DecryptFragment by lazy {
        parentFragment!!.parentFragment as DecryptFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.vm = vm
        binding.decryptVm = decryptVm
        binding.lifecycleOwner = viewLifecycleOwner

        binding.encryptedData.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                v.clearFocus()
                try {
                    val imm = v.context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    v.clearFocus()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                decryptVm.decrypt(vm.toDecrypt.value!!)
            }
            true
        }

        binding.id.setOnClickListener {
            vm.userId.value = (it as TextView).text.toString()
            homeParent.navigateToHome()
        }

        binding.camera.setOnClickListener {
            parent.goToCamera()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hasResult = arguments?.getBoolean("hasResult")
        if (hasResult == true) {
            binding.encryptedData.requestFocus()
            try {
                binding.encryptedData.requestFocus()
                val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.encryptedData, InputMethodManager.SHOW_IMPLICIT)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
