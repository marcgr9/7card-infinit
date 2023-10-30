package ro.marc.sevencard.ui.fragments.qr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ro.marc.sevencard.data.User
import ro.marc.sevencard.data.repo.UsersRepo
import ro.marc.sevencard.generator.QrDataGenerator
import ro.marc.sevencard.ui.MainViewModel

class QRViewModel(
    private val qrDataGenerator: QrDataGenerator,
    private val usersRepo: UsersRepo,
    private val userId: Long,
): ViewModel() {

    companion object {
        const val QR_CODE_DELAY_MS: Long = 350
    }

    private val _progressBarProgress = MutableLiveData<Int>(0)
    val progressBarProgress: LiveData<Int>
        get() = _progressBarProgress

    val encodedDataFlow: Flow<String> = flow {
        while (true) {
            emit(qrDataGenerator.encode(userId))
            _progressBarProgress.value = (_progressBarProgress.value!! + 5).takeIf { it <= 100 } ?: 0
            delay(QR_CODE_DELAY_MS)
        }
    }

    private val _isIdSaved = MutableLiveData<Boolean>()
    val isIdSaved: LiveData<Boolean>
        get() = _isIdSaved

    fun save() {
        if (_isIdSaved.value == true) return

        viewModelScope.launch {
            usersRepo.save(User(userId, null)).collect {
                _isIdSaved.value = true
            }
        }
    }

}
