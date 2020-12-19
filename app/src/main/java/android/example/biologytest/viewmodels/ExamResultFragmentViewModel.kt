package android.example.biologytest.viewmodels

import android.example.biologytest.repository.ExamRepository
import android.example.biologytest.repository.QuestionRepository
import android.example.biologytest.util.Event
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ExamResultFragmentViewModel
@ViewModelInject
constructor(
    private val examRepository: ExamRepository,
    private val questionRepository: QuestionRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val exam = examRepository.getLatestExam()

    val questionsCount = exam.switchMap {
        questionRepository.getCountQuestion(it.Id)
    }

    val answered = exam.switchMap {
        questionRepository.getCountAnsweredQuestion(it.Id)
    }

    val answeredCorrectly = exam.switchMap {
        questionRepository.getCountCorrect(it.Id)
    }

    fun endExamResult() {
        _navigateToOpeningFragment.value = Event(Unit)
    }

    //region Navigation
    private val _navigateToOpeningFragment = MutableLiveData<Event<Unit>>()
    val navigateToOpeningFragment: LiveData<Event<Unit>> = _navigateToOpeningFragment
    //endregion
}