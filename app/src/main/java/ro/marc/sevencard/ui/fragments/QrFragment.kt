package ro.marc.sevencard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.logging.type.LogSeverity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.sevencard.databinding.FragmentQrBinding
import ro.marc.sevencard.ui.MainActivity
import ro.marc.sevencard.ui.MainViewModel

class QrFragment: BaseFragment<MainActivity, FragmentQrBinding>() {

    private val vm: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.generateButton.setOnClickListener {
            vm.navigateTo(MainViewModel.NavigationCase.Home)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.encodedDataFlow.collect {
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

        return binding.root
    }

}
