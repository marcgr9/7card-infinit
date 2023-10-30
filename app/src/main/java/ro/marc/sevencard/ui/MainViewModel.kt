package ro.marc.sevencard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ro.marc.sevencard.generator.QrDataGenerator
import ro.marc.sevencard.util.SingleLiveEvent

class MainViewModel(
    private val qrDataGenerator: QrDataGenerator,
): ViewModel() {

    companion object {
        const val QR_CODE_DELAY_MS: Long = 350
    }

    sealed class NavigationCase {
        object Home: NavigationCase()
        object Qr: NavigationCase()
    }

    val encodedDataFlow: Flow<String> = flow {
        while (true) {
            emit(qrDataGenerator.encode(userId.value!!.toLong()))
            delay(QR_CODE_DELAY_MS)
        }
    }

    private val _navigationEvent = SingleLiveEvent<NavigationCase>()
    val navigationEvent: LiveData<NavigationCase>
        get() = _navigationEvent

    val userId = MutableLiveData("")

    fun navigateTo(navigationCase: NavigationCase) {
        _navigationEvent.value = navigationCase
    }

}
