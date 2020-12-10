package android.example.biologytest.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DEFINED_ANSWER")
data class DefinedAnswerEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val Id: Long,
    @ColumnInfo(name = "TEXT") val Text: String,
    @ColumnInfo(name = "QUESTION_ID") val QuestionId: Long,
    @ColumnInfo(name = "CORRECT") val IsCorrect: Boolean
)