package android.example.biologytest.repository

import android.example.biologytest.model.daos.DefinedAnswerDao
import android.example.biologytest.model.entities.DefinedAnswerEntity
import javax.inject.Inject

class DefinedAnswerRepository @Inject constructor(
    private val definedAnswerDao: DefinedAnswerDao
) {

    suspend fun insert(definedAnswerEntity: DefinedAnswerEntity) =
        definedAnswerDao.insert(definedAnswerEntity)

    suspend fun getRawList(QUESTION_ID:Long) =
        definedAnswerDao.getRawList(QUESTION_ID)
}