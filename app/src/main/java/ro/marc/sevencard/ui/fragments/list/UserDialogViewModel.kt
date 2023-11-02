package ro.marc.sevencard.ui.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.marc.sevencard.data.User
import ro.marc.sevencard.data.repo.UsersRepo
import ro.marc.sevencard.util.SingleLiveEvent

class UserDialogViewModel(
    private val usersRepo: UsersRepo,
    private val user: User,
): ViewModel() {

    val alias = MutableLiveData(user.alias)
    val title: LiveData<String> = MutableLiveData(user.id.toString())

    enum class DismissType {
        DELETED,
        SAVED,
    }

    private val _dismissEvent = SingleLiveEvent<DismissType>()
    val dismissEvent: LiveData<DismissType>
        get() = _dismissEvent

    fun save() {
        viewModelScope.launch {
            usersRepo.save(user.copy(alias = alias.value.takeIf { it!!.isNotBlank() })).collect {
                if (it != -1L) {
                    _dismissEvent.value = DismissType.SAVED
                }
            }
        }
    }

    fun remove() {
        viewModelScope.launch {
            usersRepo.deleteById(user.id).collect {
                if (it > 0) {
                    _dismissEvent.value = DismissType.DELETED
                }
            }
        }
    }

}
