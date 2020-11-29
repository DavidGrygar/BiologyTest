package android.example.biologytest.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ANSWER")
class AnswerEntity(
    @PrimaryKey(autoGenerate = true) val ID: Long = 0,
    var TEXT: String? = null,
    val QUESTION_ID: Long = 0,
    var EXAM_ID: Long = 0
)