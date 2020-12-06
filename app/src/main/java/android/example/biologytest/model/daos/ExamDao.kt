package android.example.biologytest.model.daos

import android.example.biologytest.model.entities.DefinedAnswerEntity
import android.example.biologytest.model.entities.ExamEntity
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExamDao: BaseDao<ExamEntity> {
    @Query("SELECT * FROM EXAM WHERE ID = :ID LIMIT 1")
    fun getSingle(ID: Long): LiveData<ExamEntity>

    @Query("SELECT * FROM EXAM ORDER BY ID DESC LIMIT 1")
    fun getLatest(): LiveData<ExamEntity>
}