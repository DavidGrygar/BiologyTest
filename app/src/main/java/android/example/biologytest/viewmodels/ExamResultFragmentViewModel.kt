package android.example.biologytest.viewmodels

import android.example.biologytest.repository.AnswerRepository
import android.example.biologytest.repository.DefinedAnswerRepository
import android.example.biologytest.repository.ExamRepository
import android.example.biologytest.repository.QuestionRepository
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
        questionRepository.getCountQuestion(it.ID)
    }

    val answered = exam.switchMap {
        questionRepository.getCountAnsweredQuestion(it.ID)
    }

    val answeredCorrectly = exam.switchMap {
        questionRepository.getCountCorrect(it.ID)
    }
}