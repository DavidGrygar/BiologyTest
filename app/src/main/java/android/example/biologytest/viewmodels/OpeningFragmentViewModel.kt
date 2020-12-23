package android.example.biologytest.viewmodels

import android.example.biologytest.model.entities.TopicGroupEntity
import android.example.biologytest.repository.ExamRepository
import android.example.biologytest.repository.TopicGroupRepository
import android.example.biologytest.util.Event
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class OpeningFragmentViewModel
@ViewModelInject
constructor(
    private val topicGroupRepository: TopicGroupRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {
    private val _navigateToExamFragment = MutableLiveData<Event<Unit>>()
    val navigateToExamFragment: LiveData<Event<Unit>> = _navigateToExamFragment

    fun startExam() {
        _navigateToExamFragment.value = Event(Unit)
    }

    val topicGroups: LiveData<List<TopicGroupEntity>> = liveData {
        // some additional work
        topicGroupRepository.getAllTopicGroups().collect {
            emit(it)
        }
    }
}