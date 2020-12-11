package android.example.biologytest.adapters

import android.example.biologytest.factories.AnswerHandler
import android.example.biologytest.factories.QuestionListItemViewHolder
import android.example.biologytest.factories.QuestionListItemViewHolderFactory
import android.example.biologytest.model.QuestionRow
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi


class QuestionListAdapter @ExperimentalCoroutinesApi constructor(val answerHandler: AnswerHandler) :
    ListAdapter<QuestionRow, QuestionListItemViewHolder>(
        QuestionRowDiffCallback()
    ) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).questionEntity.QuestionType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionListItemViewHolder {
        return QuestionListItemViewHolderFactory.createQuestionListItemViewHolder(
            parent,
            viewType,
            answerHandler
        )
    }

    @ExperimentalCoroutinesApi
    override fun onBindViewHolder(holder: QuestionListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class QuestionRowDiffCallback : DiffUtil.ItemCallback<QuestionRow>() {
    override fun areItemsTheSame(oldItem: QuestionRow, newItem: QuestionRow): Boolean {
        return oldItem.questionEntity.Id == newItem.questionEntity.Id
    }

    override fun areContentsTheSame(oldItem: QuestionRow, newItem: QuestionRow): Boolean {
        return oldItem.equals(newItem)
    }
}