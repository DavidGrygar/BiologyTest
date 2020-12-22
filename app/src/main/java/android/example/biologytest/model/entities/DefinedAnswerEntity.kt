package android.example.biologytest.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DEFINED_ANSWER")
data class DefinedAnswerEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Long,
    @ColumnInfo(name = "TEXT") val text: String,
    @ColumnInfo(name = "QUESTION_ID") val questionId: Long,
    @ColumnInfo(name = "CORRECT") val isCorrect: Boolean
)