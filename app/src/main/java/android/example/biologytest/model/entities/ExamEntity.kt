package android.example.biologytest.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "EXAM")
data class ExamEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Long,
    @ColumnInfo(name = "FINISHED") val finished: Boolean = false,
    @ColumnInfo(name = "TS_CREATED") val timeStampCreated: Date
)