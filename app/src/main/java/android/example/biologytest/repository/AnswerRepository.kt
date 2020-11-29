package android.example.biologytest.repository

import android.example.biologytest.model.daos.AnswerDao
import android.example.biologytest.model.entities.AnswerEntity
import javax.inject.Inject

class AnswerRepository @Inject constructor(
    private val answerDao: AnswerDao
) {
    suspend fun insert(answerEntity: AnswerEntity) =
        answerDao.insert(answerEntity)

    suspend fun insertAll(answers: List<AnswerEntity>) =
        answerDao.insertAll(answers)

    fun getRawList(EXAM_ID: Long, QUESTION_ID: Long) =
        answerDao.getRawList(EXAM_ID, QUESTION_ID)
}