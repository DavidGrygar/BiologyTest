package android.example.biologytest.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ANSWER")
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val Id: Long = 0,
    @ColumnInfo(name = "TEXT") var Text: String? = null,
    @ColumnInfo(name = "QUESTION_ID") val QuestionId: Long = 0,
    @ColumnInfo(name = "EXAM_ID") var ExamId: Long = 0
)