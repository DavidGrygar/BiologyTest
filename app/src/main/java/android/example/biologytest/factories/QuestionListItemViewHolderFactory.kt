package android.example.biologytest.factories

import android.example.biologytest.databinding.QuestionRowMultipleCorectAnswerBinding
import android.example.biologytest.databinding.QuestionRowNumberBinding
import android.example.biologytest.databinding.QuestionRowSingleCorrectAnswerBinding
import android.example.biologytest.enums.QuestionTypeEnum
import android.example.biologytest.model.QuestionRow
import android.example.biologytest.model.entities.QuestionEntity
import android.example.biologytest.ui.hideKeyboard
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView

class QuestionListItemViewHolderFactory {

    companion object {
        fun createQuestionListItemViewHolder(
            parent: ViewGroup,
            viewType: Int,
            answerHandler: AnswerHandler
        ): QuestionListItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return when (viewType) {
                QuestionTypeEnum.SPECIFIC_NUMBER.ordinal -> QuestionListItemViewHolder.SpecificNumberViewHolder(
                    QuestionRowNumberBinding.inflate(layoutInflater, parent, false),
                    answerHandler
                )
                QuestionTypeEnum.SINGLE_CORRECT.ordinal -> QuestionListItemViewHolder.SingleCorrectViewHolder(
                    QuestionRowSingleCorrectAnswerBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    ),
                    answerHandler
                )
                QuestionTypeEnum.MULTIPLE_CORRECT.ordinal -> QuestionListItemViewHolder.MultipleCorrectViewHolder(
                    QuestionRowMultipleCorectAnswerBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    ),
                    answerHandler
                )
                else -> throw IllegalStateException()
            }
        }
    }

}

sealed class QuestionListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class SpecificNumberViewHolder(
        val binding: QuestionRowNumberBinding,
        val answerHandler: AnswerHandler
    ) :
        QuestionListItemViewHolder(binding.root) {
        override fun bind(item: QuestionRow) {
            binding.question = item.questionEntity
            binding.questionRowNumberAnswer.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEND -> {
                        v.clearFocus()
                        v.hideKeyboard()
                        true
                    }
                    else -> false
                }
            }

            binding.questionRowNumberAnswer.doAfterTextChanged {
                answerHandler.putOrEditAnswer(item.questionEntity, it.toString())
            }
        }
    }

    class SingleCorrectViewHolder(
        val binding: QuestionRowSingleCorrectAnswerBinding,
        val answerHandler: AnswerHandler
    ) :
        QuestionListItemViewHolder(binding.root) {
        override fun bind(item: QuestionRow) {
            binding.question = item.questionEntity
            item.definedAnswersList.forEach {
                val radioButton = RadioButton(binding.root.context)
                radioButton.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                radioButton.setText(it.TEXT)
                radioButton.id = it.ID.toInt()

                val definedAnswerEntity = it

                radioButton.setOnClickListener {
                    answerHandler.putOrEditAnswer(
                        item.questionEntity,
                        definedAnswerEntity.TEXT
                    )
                }

                binding.questionRowSingleCorrectAnswerRadioGroup.addView(radioButton)
            }
        }

    }

    class MultipleCorrectViewHolder(
        val binding: QuestionRowMultipleCorectAnswerBinding,
        val answerHandler: AnswerHandler
    ) :
        QuestionListItemViewHolder(binding.root) {
        override fun bind(item: QuestionRow) {
            binding.question = item.questionEntity
            item.definedAnswersList.forEach {
                val checkBox = CheckBox(binding.root.context)
                checkBox.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                checkBox.setText(it.TEXT)
                checkBox.id = it.ID.toInt()

                val definedAnswerEntity = it

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        answerHandler.putOrEditAnswer(
                            item.questionEntity,
                            definedAnswerEntity.TEXT
                        )
                    } else {
                        answerHandler.removeAnswer(
                            item.questionEntity,
                            definedAnswerEntity.TEXT
                        )
                    }
                }

                binding.questionRowMultipleCorrectAnswerGroup.addView(checkBox)
            }
        }

    }

    abstract fun bind(item: QuestionRow)
}

interface AnswerHandler {
    fun putOrEditAnswer(questionEntity: QuestionEntity, text: String)
    fun removeAnswer(questionEntity: QuestionEntity, text: String)
}