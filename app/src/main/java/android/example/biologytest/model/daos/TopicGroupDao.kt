package android.example.biologytest.model.daos

import android.example.biologytest.model.entities.AnswerEntity
import android.example.biologytest.model.entities.TopicGroupEntity
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicGroupDao: BaseDao<TopicGroupEntity> {
    @Query("SELECT * FROM TOPIC_GROUP WHERE ID = :id LIMIT 1")
    fun getSingle(id: Long): Flow<TopicGroupEntity>

    @Query("SELECT * FROM TOPIC_GROUP")
    fun getList(): Flow<List<TopicGroupEntity>>
}