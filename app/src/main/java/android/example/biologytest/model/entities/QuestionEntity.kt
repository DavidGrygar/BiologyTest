package android.example.biologytest.model.entities

import android.example.biologytest.enums.QuestionTypeEnum
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QUESTION")
class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val ID: Long,
    val QUESTION_TYPE: QuestionTypeEnum,
    val TEXT: String
)