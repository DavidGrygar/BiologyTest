package android.example.biologytest.util

import android.example.biologytest.enums.QuestionTypeEnum
import androidx.room.TypeConverter

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
}