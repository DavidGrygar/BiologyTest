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
    private val _navigateToExamFragment = MutableLiveData<Event<Long>>()
    val navigateToExamFragment: LiveData<Event<Long>> = _navigateToExamFragment

    fun rowClick(topicGroupId: Long) {
        _navigateToExamFragment.value = Event(topicGroupId)
    }

    val topicGroups: LiveData<List<TopicGroupEntity>> = liveData {
        // some additional work
        topicGroupRepository.getAllTopicGroups().collect {
            emit(it)
        }
    }
}