package android.example.biologytest.repository

import android.example.biologytest.model.daos.ExamDao
import android.example.biologytest.model.entities.ExamEntity
import javax.inject.Inject

class ExamRepository @Inject constructor(
    private val examDao: ExamDao
) {
    suspend fun insertSingleWithLongReturn(examEntity: ExamEntity): Long =
        examDao.insertSingleWithLongReturn(examEntity)

    fun getLatestExam() =
        examDao.getLatest()
}