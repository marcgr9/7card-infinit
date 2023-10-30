package ro.marc.sevencard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ro.marc.sevencard.generator.QrDataGenerator
import ro.marc.sevencard.util.SingleLiveEvent

class MainViewModel(
    private val qrDataGenerator: QrDataGenerator,
): ViewModel() {

    sealed class NavigationCase {
        object Home: NavigationCase()
        object Qr: NavigationCase()
    }

    val encodedData: String
        get() = qrDataGenerator.encode(userId.value!!.toLong())

    private val _navigationEvent = SingleLiveEvent<NavigationCase>()
    val navigationEvent: LiveData<NavigationCase>
        get() = _navigationEvent

    val userId = MutableLiveData("")

    fun navigateTo(navigationCase: NavigationCase) {
        _navigationEvent.value = navigationCase
    }

}
