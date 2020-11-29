package android.example.biologytest.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DEFINED_ANSWER")
class DefinedAnswerEntity(
    @PrimaryKey(autoGenerate = true) val ID: Long,
    val TEXT: String,
    val QUESTION_ID: Long,
    val CORRECT: Boolean
)