package android.example.biologytest.adapters

import android.example.biologytest.model.QuestionRow
import android.example.biologytest.databinding.QuestionRowMultipleCorectAnswerBinding
import android.example.biologytest.databinding.QuestionRowNumberBinding
import android.example.biologytest.databinding.QuestionRowSingleCorrectAnswerBinding
import android.example.biologytest.enums.QuestionTypeEnum
import android.example.biologytest.ui.hideKeyboard
import android.example.biologytest.viewmodels.ExamFragmentViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi


class QuestionListAdapter2 @ExperimentalCoroutinesApi constructor(val examFragmentViewModel: ExamFragmentViewModel) :
    ListAdapter<QuestionRow, QuestionListItemViewHolder>(
        QuestionRowDiffCallback()
    ) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).questionEntity.QUESTION_TYPE.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionListItemViewHolder {
        return QuestionListItemViewHolder.from(parent, viewType)
    }

    @ExperimentalCoroutinesApi
    override fun onBindViewHolder(holder: QuestionListItemViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is QuestionListItemViewHolder.SpecificNumberViewHolder -> {
                holder.binding.question = item.questionEntity
                holder.binding.questionRowNumberAnswer.setOnEditorActionListener { v, actionId, event ->
                    return@setOnEditorActionListener when (actionId) {
                        EditorInfo.IME_ACTION_SEND -> {
                            v.clearFocus()
                            v.hideKeyboard()
                            //examFragmentViewModel.changeFilledAnswer(item.questionEntity, v.text.toString())
                            true
                        }
                        else -> false
                    }
                }

                holder.binding.questionRowNumberAnswer.doAfterTextChanged {
                    examFragmentViewModel.putAnswer(item.questionEntity, it.toString())
                }
            }
            is QuestionListItemViewHolder.SingleCorrectViewHolder -> {
                holder.binding.question = item.questionEntity
                item.definedAnswersList.forEach {
                    val radioButton = RadioButton(holder.binding.root.context)
                    radioButton.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    radioButton.setText(it.TEXT)
                    radioButton.id = it.ID.toInt()

                    val definedAnswerEntity = it

                    radioButton.setOnClickListener(View.OnClickListener {
                        examFragmentViewModel.putAnswer(
                            item.questionEntity,
                            definedAnswerEntity.TEXT
                        )
                    })

                    holder.binding.questionRowSingleCorrectAnswerRadioGroup.addView(radioButton)
                }

            }
            is QuestionListItemViewHolder.MultipleCorrectViewHolder -> {
                holder.binding.question = item.questionEntity
                item.definedAnswersList.forEach {
                    val checkBox = CheckBox(holder.binding.root.context)
                    checkBox.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                    checkBox.setText(it.TEXT)
                    checkBox.id = it.ID.toInt()

                    val definedAnswerEntity = it

                    checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
                        if (isChecked) {
                            examFragmentViewModel.putAnswer(
                                item.questionEntity,
                                definedAnswerEntity.TEXT
                            )
                        }
                        else
                        {
                            examFragmentViewModel.removeAnswer(
                                item.questionEntity,
                                definedAnswerEntity.TEXT
                            )
                        }
                    }

                    holder.binding.questionRowMultipleCorrectAnswerGroup.addView(checkBox)
                }
            }
        }
    }
}

sealed class QuestionListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class SpecificNumberViewHolder(val binding: QuestionRowNumberBinding) :
        QuestionListItemViewHolder(
            binding.root
        )

    class SingleCorrectViewHolder(val binding: QuestionRowSingleCorrectAnswerBinding) :
        QuestionListItemViewHolder(
            binding.root
        ) {
        /*init {
            val radioButton1 = RadioButton(binding.root.context)
            radioButton1.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            radioButton1.setText("aaa")
            radioButton1.id = View.generateViewId()

            val radioButton2 = RadioButton(binding.root.context)
            radioButton2.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            radioButton2.setText("bbb")
            radioButton2.id = View.generateViewId()

            binding.questionRowSingleCorrectAnswerRadioGroup.addView(radioButton1)
            binding.questionRowSingleCorrectAnswerRadioGroup.addView(radioButton2)
        }*/
    }

    class MultipleCorrectViewHolder(val binding: QuestionRowMultipleCorectAnswerBinding) :
        QuestionListItemViewHolder(
            binding.root
        )

    companion object {
        fun from(parent: ViewGroup, viewType: Int): QuestionListItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                QuestionTypeEnum.SPECIFIC_NUMBER.ordinal -> SpecificNumberViewHolder(
                    QuestionRowNumberBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
                QuestionTypeEnum.SINGLE_CORRECT.ordinal -> SingleCorrectViewHolder(
                    QuestionRowSingleCorrectAnswerBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
                QuestionTypeEnum.MULTIPLE_CORRECT.ordinal -> MultipleCorrectViewHolder(
                    QuestionRowMultipleCorectAnswerBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
                else -> throw IllegalStateException()
            }
        }
    }
}

class QuestionRowDiffCallback : DiffUtil.ItemCallback<QuestionRow>() {
    override fun areItemsTheSame(oldItem: QuestionRow, newItem: QuestionRow): Boolean {
        return oldItem.questionEntity.ID == newItem.questionEntity.ID
    }

    override fun areContentsTheSame(oldItem: QuestionRow, newItem: QuestionRow): Boolean {
        return oldItem.equals(newItem)
    }
}