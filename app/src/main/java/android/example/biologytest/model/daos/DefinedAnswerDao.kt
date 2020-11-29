package android.example.biologytest.model.daos

import android.example.biologytest.model.entities.DefinedAnswerEntity
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DefinedAnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(definedAnswerEntity: DefinedAnswerEntity)

    @Query("SELECT * FROM DEFINED_ANSWER WHERE ID = :ID LIMIT 1")
    fun getSingle(ID: Long): LiveData<DefinedAnswerEntity>

    @Query("SELECT * FROM DEFINED_ANSWER WHERE QUESTION_ID = :QUESTION_ID")
    fun getList(QUESTION_ID: Long): LiveData<List<DefinedAnswerEntity>>

    @Query("SELECT * FROM DEFINED_ANSWER WHERE QUESTION_ID = :QUESTION_ID")
    suspend fun getRawList(QUESTION_ID: Long): List<DefinedAnswerEntity>

    /*@Query("SELECT * FROM ANSWER WHERE QUESTION_ID = :QUESTION_ID AND CORRECT = CORRECT")
    suspend fun getList(QUESTION_ID: Long, CORRECT: Boolean): List<AnswerEntity>*/
}