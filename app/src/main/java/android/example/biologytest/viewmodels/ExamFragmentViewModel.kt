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
import android.example.biologytest.util.Event
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

    //region Navigation
    private val _navigateToExamResultFragment = MutableLiveData<Event<Unit>>()
    val navigateToExamResultFragment: LiveData<Event<Unit>> = _navigateToExamResultFragment
    //endregion

    fun putOrEditAnswer(questionEntity: QuestionEntity, text: String) {
        when (questionEntity.QuestionType) {
            QuestionTypeEnum.MULTIPLE_CORRECT -> {
                answersMap.getOrPut(questionEntity.Id) { mutableSetOf(text) }.add(text)
            }
            else -> {
                answersMap.put(questionEntity.Id, mutableSetOf(text))
            }
        }
    }

    fun removeAnswer(questionEntity: QuestionEntity, text: String) {
        when (questionEntity.QuestionType) {
            QuestionTypeEnum.MULTIPLE_CORRECT -> {
                answersMap.get(questionEntity.Id)?.remove(text)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    fun setQuetionRows() {
        viewModelScope.launch {
            val questionRowsList = mutableListOf<QuestionRow>()
            questionRepository.getAllQuestionsRaw().map {
                when (it.QuestionType) {
                    QuestionTypeEnum.SPECIFIC_NUMBER -> {
                        questionRowsList.add(QuestionRow(it, emptyList()))
                    }
                    QuestionTypeEnum.SINGLE_CORRECT -> {
                        questionRowsList.add(
                            QuestionRow(
                                it,
                                definedAnswerRepository.getRawList(it.Id)
                            )
                        )
                    }
                    QuestionTypeEnum.MULTIPLE_CORRECT -> {
                        questionRowsList.add(
                            QuestionRow(
                                it,
                                definedAnswerRepository.getRawList(it.Id)
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

                val answerListForQuestion = answersMap.get(questionEntity.Id)

                if (answerListForQuestion.isNullOrEmpty()) {
                    allAnswers.add(AnswerEntity(QuestionId = questionEntity.Id, ExamId = examID))
                }
                else
                {
                    answerListForQuestion.forEach{
                        allAnswers.add(AnswerEntity(QuestionId = questionEntity.Id, Text = it, ExamId = examID))
                    }
                }
            }

            answerRepository.insertList(allAnswers)
            _navigateToExamResultFragment.value = Event(Unit)
        }
    }
}