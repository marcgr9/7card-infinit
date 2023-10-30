package ro.marc.sevencard.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val userId = MutableLiveData<String>()

}
