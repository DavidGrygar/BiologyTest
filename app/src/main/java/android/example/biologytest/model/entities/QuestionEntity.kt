package android.example.biologytest.model.entities

import android.example.biologytest.enums.QuestionTypeEnum
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QUESTION")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Long,
    @ColumnInfo(name = "QUESTION_TYPE") val questionType: QuestionTypeEnum,
    @ColumnInfo(name = "TEXT") val text: String,
    @ColumnInfo(name = "TOPIC_GROUP_ID") val topicGroupId: Long
)