package android.example.biologytest.model.entities

import android.example.biologytest.enums.QuestionTypeEnum
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QUESTION")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val Id: Long,
    @ColumnInfo(name = "QUESTION_TYPE") val QuestionType: QuestionTypeEnum,
    @ColumnInfo(name = "TEXT") val Text: String
)