package ro.marc.sevencard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ro.marc.sevencard.generator.QrDataGenerator
import ro.marc.sevencard.util.SingleLiveEvent

class MainViewModel: ViewModel() {

    sealed class NavigationCase {
        object Home: NavigationCase()
        object Qr: NavigationCase()
    }

    private val _navigationEvent = SingleLiveEvent<NavigationCase>()
    val navigationEvent: LiveData<NavigationCase>
        get() = _navigationEvent

    val userId = MutableLiveData("")
    val toDecrypt = MutableLiveData("")

    fun navigateTo(navigationCase: NavigationCase) {
        _navigationEvent.value = navigationCase
    }

}
