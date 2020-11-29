package android.example.biologytest.model.daos

import android.example.biologytest.model.entities.QuestionEntity
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questionEntity: QuestionEntity)

    @Query("SELECT * FROM QUESTION WHERE ID = :ID")
    fun getSingle(ID: Long): LiveData<QuestionEntity>

    @Query("SELECT * FROM QUESTION")
    fun getList(): LiveData<List<QuestionEntity>>

    @Query("SELECT DISTINCT Q.* FROM QUESTION AS Q INNER JOIN ANSWER A ON A.QUESTION_ID = Q.ID WHERE EXAM_ID = :EXAM_ID")
    fun getList(EXAM_ID: Long): LiveData<List<QuestionEntity>>

    @Query("SELECT * FROM QUESTION")
    suspend fun getRawList(): List<QuestionEntity>

    @Query("SELECT COUNT(*) FROM QUESTION AS Q LEFT JOIN (SELECT DA.QUESTION_ID AS QID, SUM(CASE WHEN (DA.CORRECT = 1 AND A.TEXT ISNULL) THEN 1 WHEN (DA.CORRECT = 0 AND A.TEXT IS NOT NULL) THEN 1 ELSE 0 END) AS WRONG_SUM FROM DEFINED_ANSWER AS DA LEFT JOIN ANSWER AS A ON A.QUESTION_ID = DA.QUESTION_ID AND A.EXAM_ID = :EXAM_ID AND A.TEXT = DA.TEXT GROUP BY QID) AS SUB_JOIN ON SUB_JOIN.QID = Q.ID WHERE SUB_JOIN.WRONG_SUM = 0")
    fun getCountCorrect(EXAM_ID: Long): LiveData<Int>

    @Query("SELECT COUNT(DISTINCT Q.ID) FROM QUESTION AS Q INNER JOIN ANSWER A ON A.QUESTION_ID = Q.ID WHERE EXAM_ID = :EXAM_ID")
    fun getCountQuestion(EXAM_ID: Long): LiveData<Int>

    @Query("SELECT COUNT(DISTINCT Q.ID) FROM QUESTION AS Q INNER JOIN ANSWER A ON A.QUESTION_ID = Q.ID AND A.TEXT IS NOT NULL WHERE EXAM_ID = :EXAM_ID")
    fun getCountAnsweredQuestion(EXAM_ID: Long): LiveData<Int>
}