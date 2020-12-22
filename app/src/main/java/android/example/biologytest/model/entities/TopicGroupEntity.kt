package android.example.biologytest.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TOPIC_GROUP")
data class TopicGroupEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Long = 0,
    @ColumnInfo(name = "NAME") var name: String? = null,
    @ColumnInfo(name = "DESCRIPTION") var description: String? = null
)