package android.example.biologytest.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EXAM")
class ExamEntity(
    @PrimaryKey(autoGenerate = true) val ID: Long,
    val FINISHED: Boolean = false
)