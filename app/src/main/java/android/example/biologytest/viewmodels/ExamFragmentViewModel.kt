package android.example.biologytest.viewmodels

import android.example.biologytest.enums.QuestionTypeEnum
import android.example.biologytest.model.QuestionRow
import android.example.biologytest.model.entities.AnswerEntity
import android.example.biologytest.model.entities.ExamEntity
import android.example.biologytest.model.entities.QuestionEntity
import android.example.biologytest.model.entities.TopicGroupEntity
import android.example.biologytest.repository.*
import android.example.biologytest.util.Event
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
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
    private val topicGroupRepository: TopicGroupRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val answersMap = mutableMapOf<Long, MutableSet<String>>()

    private val _topicGroupId = MutableLiveData<Long>()

    private val _topicGroup = _topicGroupId.switchMap { id ->
        topicGroupRepository.getTopicGroup(id).asLiveData()
    }

    val questionRows : LiveData<List<QuestionRow>> = _topicGroupId.switchMap { id ->
        liveData {
            questionRepository.getQuestionsForTopicGroup(id).collect {
                it.map {
                    when (it.questionType) {
                        QuestionTypeEnum.SPECIFIC_NUMBER -> {
                            QuestionRow(it, emptyList())
                        }
                        QuestionTypeEnum.SINGLE_CORRECT -> {
                            QuestionRow(it,definedAnswerRepository.getRawList(it.id))
                        }
                        QuestionTypeEnum.MULTIPLE_CORRECT -> {
                            QuestionRow(it,definedAnswerRepository.getRawList(it.id))
                        }
                    }
                }.also { emit(it) }
            }
        }

    }

    /*init {
        initQuetionRows()
    }*/

    fun start(topicGroupId: Long?) {
        _topicGroupId.value = topicGroupId
    }

    //region Navigation
    private val _navigateToExamResultFragment = MutableLiveData<Event<Unit>>()
    val navigateToExamResultFragment: LiveData<Event<Unit>> = _navigateToExamResultFragment
    //endregion

    fun putOrEditAnswer(questionEntity: QuestionEntity, text: String) {
        when (questionEntity.questionType) {
            QuestionTypeEnum.MULTIPLE_CORRECT -> {
                answersMap.getOrPut(questionEntity.id) { mutableSetOf(text) }.add(text)
            }
            else -> {
                answersMap.put(questionEntity.id, mutableSetOf(text))
            }
        }
    }

    fun removeAnswer(questionEntity: QuestionEntity, text: String) {
        when (questionEntity.questionType) {
            QuestionTypeEnum.MULTIPLE_CORRECT -> {
                answersMap.get(questionEntity.id)?.remove(text)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    fun initQuetionRows() {
        viewModelScope.launch {

            questionRepository


            /*val questionRowsList = mutableListOf<QuestionRow>()
            questionRepository.getAllQuestionsRaw().map {
                when (it.questionType) {
                    QuestionTypeEnum.SPECIFIC_NUMBER -> {
                        questionRowsList.add(QuestionRow(it, emptyList()))
                    }
                    QuestionTypeEnum.SINGLE_CORRECT -> {
                        questionRowsList.add(
                            QuestionRow(
                                it,
                                definedAnswerRepository.getRawList(it.id)
                            )
                        )
                    }
                    QuestionTypeEnum.MULTIPLE_CORRECT -> {
                        questionRowsList.add(
                            QuestionRow(
                                it,
                                definedAnswerRepository.getRawList(it.id)
                            )
                        )
                    }
                }
            }
            questionRows.value = questionRowsList*/
        }
    }

    fun finishExam() {
        viewModelScope.launch {
            val examEntity = ExamEntity(0, true, Date())
            val examID = examRepository.insertSingleWithLongReturn(examEntity)
            val allAnswers = mutableListOf<AnswerEntity>()

            questionRows.value?.forEach {

                val questionEntity = it.questionEntity

                val answerListForQuestion = answersMap.get(questionEntity.id)

                if (answerListForQuestion.isNullOrEmpty()) {
                    allAnswers.add(AnswerEntity(questionId = questionEntity.id, examId = examID))
                } else {
                    answerListForQuestion.forEach {
                        allAnswers.add(
                            AnswerEntity(
                                questionId = questionEntity.id,
                                text = it,
                                examId = examID
                            )
                        )
                    }
                }
            }

            answerRepository.insertList(allAnswers)
            _navigateToExamResultFragment.value = Event(Unit)
        }
    }
}