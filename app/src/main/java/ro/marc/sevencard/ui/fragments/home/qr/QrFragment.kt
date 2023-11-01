package ro.marc.sevencard.ui.fragments.home.qr

import android.os.Bundle
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.lifecycle.lifecycleScope
import com.google.logging.type.LogSeverity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import ro.marc.sevencard.R
import ro.marc.sevencard.databinding.FragmentQrBinding
import ro.marc.sevencard.ui.MainActivity
import ro.marc.sevencard.ui.MainViewModel
import ro.marc.sevencard.ui.fragments.BaseFragment

class QrFragment: BaseFragment<MainActivity, FragmentQrBinding>() {

    private val vm: MainViewModel by sharedViewModel()
    private val qrVm: QRViewModel by lazy {
        this@QrFragment.getViewModel {
            parametersOf(vm.userId.value!!.toLong())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.dismissButton.setOnClickListener {
            vm.navigateTo(MainViewModel.NavigationCase.Home)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            qrVm.encodedDataFlow.collect {
                binding.qr.setImageBitmap(
                    BarcodeEncoder().createBitmap(
                        MultiFormatWriter().encode(
                            it,
                            BarcodeFormat.QR_CODE,
                            LogSeverity.NOTICE_VALUE,
                            LogSeverity.NOTICE_VALUE ,
                        )
                    )
                )
            }
        }

        qrVm.progressBarProgress.observe(viewLifecycleOwner) {
            binding.progressBar.progress = it
        }

        binding.accountNumber.text = buildSpannedString {
            append(
                getString(R.string.qr_account_number)
            )
            append(" ")
            append(vm.userId.value!!)
            setSpan(
                ForegroundColorSpan(resources.getColor(R.color.accent, null)),
                this.length - vm.userId.value!!.toString().length,
                this.length,
                SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }

        binding.save.setOnClickListener {
            qrVm.save()
        }

        qrVm.isIdSaved.observe(viewLifecycleOwner) {
            if (it) {
                binding.save.setColorFilter(resources.getColor(R.color.green, null))
            }
        }

        return binding.root
    }

}
