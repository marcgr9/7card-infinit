package ro.marc.sevencard.ui.fragments.decrypt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ro.marc.sevencard.data.generator.QrDataGenerator
import java.lang.Exception
import java.lang.IllegalArgumentException

class DecryptViewModel(
    private val qrDataGenerator: QrDataGenerator,
): ViewModel() {

    val decryptedId = MutableLiveData("")

    fun decrypt(base64: String) {
        decryptedId.value = try {
            qrDataGenerator.getIdFrom(base64).toString()
        } catch (e: Exception) {
            ""
        }
    }

}
