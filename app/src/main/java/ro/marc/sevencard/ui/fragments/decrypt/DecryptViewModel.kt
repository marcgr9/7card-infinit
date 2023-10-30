package ro.marc.sevencard.ui.fragments.decrypt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ro.marc.sevencard.generator.QrDataGenerator

class DecryptViewModel(
    private val qrDataGenerator: QrDataGenerator,
): ViewModel() {

    val decryptedId = MutableLiveData("")

    fun decrypt(base64: String) {
        decryptedId.value = qrDataGenerator.getIdFrom(base64)
    }

}
