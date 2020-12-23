package android.example.biologytest.model

import android.example.biologytest.model.daos.*
import android.example.biologytest.model.entities.*
import android.example.biologytest.util.MyConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [QuestionEntity::class, DefinedAnswerEntity::class, ExamEntity::class, AnswerEntity::class, TopicGroupEntity::class],
    version = 14
)
@TypeConverters(MyConverters::class)
abstract class MyDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun definedAnswerDao(): DefinedAnswerDao
    abstract fun examDao(): ExamDao
    abstract fun answerDao(): AnswerDao
    abstract fun topicGroupDao(): TopicGroupDao

    companion object {
        val DATABASE_NAME: String = "database.db"
    }
}