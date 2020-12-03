package android.example.biologytest.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "EXAM")
class ExamEntity(
    @PrimaryKey(autoGenerate = true) val ID: Long,
    val FINISHED: Boolean = false,
    val TS_CREATED: Date
)