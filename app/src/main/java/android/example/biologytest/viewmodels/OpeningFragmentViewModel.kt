package android.example.biologytest.viewmodels

import android.example.biologytest.util.Event
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
    private val _navigateToExamFragment = MutableLiveData<Event<Unit>>()
    val navigateToExamFragment: LiveData<Event<Unit>> = _navigateToExamFragment

    fun startExam() {
        _navigateToExamFragment.value = Event(Unit)
    }
}