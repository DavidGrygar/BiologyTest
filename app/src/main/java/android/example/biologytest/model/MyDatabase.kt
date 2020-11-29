package android.example.biologytest.model

import android.example.biologytest.model.daos.DefinedAnswerDao
import android.example.biologytest.model.daos.ExamDao
import android.example.biologytest.model.daos.AnswerDao
import android.example.biologytest.model.daos.QuestionDao
import android.example.biologytest.model.entities.DefinedAnswerEntity
import android.example.biologytest.model.entities.ExamEntity
import android.example.biologytest.model.entities.AnswerEntity
import android.example.biologytest.model.entities.QuestionEntity
import android.example.biologytest.util.MyConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [QuestionEntity::class, DefinedAnswerEntity::class, ExamEntity::class, AnswerEntity::class],
    version = 10,
    exportSchema = false
)
@TypeConverters(MyConverters::class)
abstract class MyDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun definedAnswerDao(): DefinedAnswerDao
    abstract fun examDao(): ExamDao
    abstract fun answerDao(): AnswerDao

    companion object {
        val DATABASE_NAME: String = "database.db"
    }
}