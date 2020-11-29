package android.example.biologytest.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class OpeningFragmentViewModel
@ViewModelInject
constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    val text = "Textou"

    private val _navigateToExamFragment = MutableLiveData<Boolean?>()
    val navigateToExamFragment: LiveData<Boolean?>
        get() = _navigateToExamFragment

    fun doneNavigating() {
        _navigateToExamFragment.value = null
    }

    fun startExam(){
        _navigateToExamFragment.value = true
    }
}