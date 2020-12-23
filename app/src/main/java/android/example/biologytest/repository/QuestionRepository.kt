package android.example.biologytest.repository

import android.example.biologytest.model.daos.QuestionDao
import android.example.biologytest.model.entities.QuestionEntity
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val questionDao: QuestionDao
) {
    suspend fun insert(questionEntity: QuestionEntity) =
        questionDao.insert(questionEntity)

    fun getAllQuestions() =
        questionDao.getList()

    fun getQuestionsForExam(EXAM_ID: Long) =
        questionDao.getListByExamId(EXAM_ID)

    fun getQuestionsForTopicGroup(topicGroupId: Long) =
        questionDao.getListByTopicGroupId(topicGroupId)

    suspend fun getAllQuestionsRaw() =
        questionDao.getRawList()

    fun getCountCorrect(EXAM_ID: Long) =
        questionDao.getCountCorrect(EXAM_ID)

    fun getCountQuestion(EXAM_ID: Long) =
        questionDao.getCountQuestion(EXAM_ID)

    fun getCountAnsweredQuestion(EXAM_ID: Long) =
        questionDao.getCountAnsweredQuestion(EXAM_ID)
}