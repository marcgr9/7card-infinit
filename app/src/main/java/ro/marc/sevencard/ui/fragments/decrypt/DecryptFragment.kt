package ro.marc.sevencard.ui.fragments.decrypt

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.sevencard.databinding.FragmentDecryptBinding
import ro.marc.sevencard.ui.MainActivity
import ro.marc.sevencard.ui.MainViewModel
import ro.marc.sevencard.ui.fragments.BaseFragment

class DecryptFragment: BaseFragment<MainActivity, FragmentDecryptBinding>() {

    private val vm: MainViewModel by sharedViewModel()
    private val decryptVm: DecryptViewModel by lazy {
        this@DecryptFragment.getViewModel()
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

        return binding.root
    }

}