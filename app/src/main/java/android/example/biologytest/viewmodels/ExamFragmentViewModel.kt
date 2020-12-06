package android.example.biologytest.viewmodels

import android.example.biologytest.model.QuestionRow
import android.example.biologytest.model.entities.ExamEntity
import android.example.biologytest.model.entities.AnswerEntity
import android.example.biologytest.model.entities.QuestionEntity
import android.example.biologytest.enums.QuestionTypeEnum
import android.example.biologytest.repository.AnswerRepository
import android.example.biologytest.repository.DefinedAnswerRepository
import android.example.biologytest.repository.ExamRepository
import android.example.biologytest.repository.QuestionRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class ExamFragmentViewModel
@ViewModelInject
constructor(
    private val examRepository: ExamRepository,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
    private val definedAnswerRepository: DefinedAnswerRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val questionRows = MutableLiveData<List<QuestionRow>>()
    val answersMap = mutableMapOf<Long, MutableSet<String>>()

    init {
        setQuetionRows()
    }

    fun putOrEditAnswer(questionEntity: QuestionEntity, text: String) {
        when (questionEntity.QUESTION_TYPE) {
            QuestionTypeEnum.MULTIPLE_CORRECT -> {
                answersMap.getOrPut(questionEntity.ID) { mutableSetOf(text) }.add(text)
            }
            else -> {
                answersMap.put(questionEntity.ID, mutableSetOf(text))
            }
        }
    }

    fun removeAnswer(questionEntity: QuestionEntity, text: String) {
        when (questionEntity.QUESTION_TYPE) {
            QuestionTypeEnum.MULTIPLE_CORRECT -> {
                answersMap.get(questionEntity.ID)?.remove(text)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    fun setQuetionRows() {
        viewModelScope.launch {
            val questionRowsList = mutableListOf<QuestionRow>()
            val questions = questionRepository.getAllQuestionsRaw()
            questions.forEach {
                when (it.QUESTION_TYPE) {
                    QuestionTypeEnum.SPECIFIC_NUMBER -> {
                        questionRowsList.add(QuestionRow(it, emptyList()))
                    }
                    QuestionTypeEnum.SINGLE_CORRECT -> {
                        questionRowsList.add(
                            QuestionRow(
                                it,
                                definedAnswerRepository.getRawList(it.ID)
                            )
                        )
                    }
                    QuestionTypeEnum.MULTIPLE_CORRECT -> {
                        questionRowsList.add(
                            QuestionRow(
                                it,
                                definedAnswerRepository.getRawList(it.ID)
                            )
                        )
                    }
                }
            }
            questionRows.value = questionRowsList
        }
    }

    fun finishExam() {
        viewModelScope.launch {
            val examEntity = ExamEntity(0, true, Date())
            val examID = examRepository.insertSingleWithLongReturn(examEntity)
            val allAnswers = mutableListOf<AnswerEntity>()

            questionRows.value?.forEach {

                val questionEntity = it.questionEntity

                val answerListForQuestion = answersMap.get(questionEntity.ID)

                if (answerListForQuestion.isNullOrEmpty()) {
                    allAnswers.add(AnswerEntity(QUESTION_ID = questionEntity.ID, EXAM_ID = examID))
                }
                else
                {
                    answerListForQuestion.forEach{
                        allAnswers.add(AnswerEntity(QUESTION_ID = questionEntity.ID, TEXT = it, EXAM_ID = examID))
                    }
                }
            }

            answerRepository.insertList(allAnswers)
            _navigateToExamResultFragment.value = true
        }
    }

    private val _navigateToExamResultFragment = MutableLiveData<Boolean?>()
    val navigateToExamResultFragment: LiveData<Boolean?>
        get() = _navigateToExamResultFragment

    fun doneNavigating() {
        _navigateToExamResultFragment.value = null
    }
}