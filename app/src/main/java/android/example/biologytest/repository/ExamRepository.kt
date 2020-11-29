package android.example.biologytest.repository

import android.example.biologytest.model.daos.ExamDao
import android.example.biologytest.model.entities.ExamEntity
import javax.inject.Inject

class ExamRepository @Inject constructor(
    private val examDao: ExamDao
) {
    suspend fun insert(examEntity: ExamEntity): Long =
        examDao.insert(examEntity)

    fun getLatestExam() =
        examDao.getLatest()
}