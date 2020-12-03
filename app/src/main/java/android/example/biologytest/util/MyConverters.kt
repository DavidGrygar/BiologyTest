package android.example.biologytest.util

import android.example.biologytest.enums.QuestionTypeEnum
import androidx.room.TypeConverter
import java.util.*

class MyConverters {
    @TypeConverter
    fun fromQuestionTypeEnumToShort(enum: QuestionTypeEnum?): Short? {
        return enum?.value
    }

    @TypeConverter
    fun fromShortToQuestionTypeEnum(short: Short?): QuestionTypeEnum? {
        try {
            return QuestionTypeEnum.values().get(short!!.toInt())
        } catch (e: Exception) {
            throw Exception("Invalid value for ANSWER.CORRECT")
        }
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

}