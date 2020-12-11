package android.example.biologytest.model.daos

import android.example.biologytest.model.entities.AnswerEntity
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface AnswerDao : BaseDao<AnswerEntity> {
    @Query("SELECT * FROM ANSWER WHERE ID = :ID LIMIT 1")
    fun getSingle(ID: Long): LiveData<AnswerEntity>

    @Query("SELECT * FROM ANSWER WHERE EXAM_ID = :EXAM_ID")
    fun getList(EXAM_ID: Long): LiveData<List<AnswerEntity>>

    @Query("SELECT * FROM ANSWER WHERE EXAM_ID = :EXAM_ID AND QUESTION_ID = :QUESTION_ID")
    fun getRawList(EXAM_ID: Long, QUESTION_ID: Long): List<AnswerEntity>
}