package android.example.biologytest.model

import android.example.biologytest.model.entities.DefinedAnswerEntity
import android.example.biologytest.model.entities.QuestionEntity

data class QuestionRow(
    val questionEntity: QuestionEntity,
    val definedAnswersList : List<DefinedAnswerEntity>
)