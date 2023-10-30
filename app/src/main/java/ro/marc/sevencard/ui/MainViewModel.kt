package ro.marc.sevencard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ro.marc.sevencard.data.User
import ro.marc.sevencard.data.local.UserDAO
import ro.marc.sevencard.data.repo.UsersRepo
import ro.marc.sevencard.generator.QrDataGenerator
import ro.marc.sevencard.util.SingleLiveEvent

class MainViewModel(
    private val usersRepo: UsersRepo,
): ViewModel() {

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

    private val _usersList = MutableLiveData<List<User>>()
    val usersList: LiveData<List<User>>
        get() = _usersList

    fun fetchUsers() {
        viewModelScope.launch {
            usersRepo.getAll().collect {
                _usersList.value = it
            }
        }
    }

}
