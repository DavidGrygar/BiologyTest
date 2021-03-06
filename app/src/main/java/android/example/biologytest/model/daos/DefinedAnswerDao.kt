package android.example.biologytest.model.daos

import android.example.biologytest.model.entities.DefinedAnswerEntity
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface DefinedAnswerDao : BaseDao<DefinedAnswerEntity> {
    @Query("SELECT * FROM DEFINED_ANSWER WHERE ID = :ID LIMIT 1")
    fun getSingle(ID: Long): LiveData<DefinedAnswerEntity>

    @Query("SELECT * FROM DEFINED_ANSWER WHERE QUESTION_ID = :QUESTION_ID")
    fun getList(QUESTION_ID: Long): LiveData<List<DefinedAnswerEntity>>

    @Query("SELECT * FROM DEFINED_ANSWER WHERE QUESTION_ID = :QUESTION_ID")
    suspend fun getRawList(QUESTION_ID: Long): List<DefinedAnswerEntity>
}