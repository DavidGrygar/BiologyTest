package android.example.biologytest.adapters

/*class QuestionListAdapter
@ExperimentalCoroutinesApi
constructor(val examFragmentViewModel: ExamFragmentViewModel): ListAdapter<QuestionListItem, QuestionListItemViewHolder>(QuestionListItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is QuestionListItem.SpecificNumber -> 0
            is QuestionListItem.SingleCorrect -> 1
            is QuestionListItem.MultipleCorrect -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionListItemViewHolder {
        return when (viewType) {
            0 -> QuestionListItemViewHolder.SpecificNumberViewHolder()
            1 -> QuestionListItemViewHolder.SingleCorrectViewHolder()
            2 -> QuestionListItemViewHolder.MultipleCorrectViewHolder()
            else -> throw IllegalStateException()
        }
    }

    /*@ExperimentalCoroutinesApi
    override fun onBindViewHolder(holder: QuestionListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, examFragmentViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionListItemViewHolder {
        return QuestionListItemViewHolder.from(parent)
    }*/

    /*class ViewHolder private constructor(val binding: QuestionRowNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @ExperimentalCoroutinesApi
        fun bind(item: QuestionListItem, examFragmentViewModel: ExamFragmentViewModel) {
            binding.question = item

            //binding.questionRowNumberAnswer.addLis
            binding.questionRowNumberAnswer.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEND -> {
                        v.clearFocus()
                        v.hideKeyboard()
                        examFragmentViewModel.changeFilledAnswer(item, v.text.toString())
                        true
                    }
                    else -> false
                }
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = QuestionRowNumberBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }*/
}

sealed class QuestionListItem{
    class SpecificNumber(val questionEntity: QuestionEntity) : QuestionListItem()
    class SingleCorrect(val questionEntity: QuestionEntity) : QuestionListItem()
    class MultipleCorrect(val questionEntity: QuestionEntity) : QuestionListItem()
}

sealed class QuestionListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class SpecificNumberViewHolder(itemView: View) : QuestionListItemViewHolder(itemView)

    class SingleCorrectViewHolder(itemView: View) : QuestionListItemViewHolder(itemView)

    class MultipleCorrectViewHolder(itemView: View) : QuestionListItemViewHolder(itemView)
}

//class QuestionEntityDiffCallback : DiffUtil.ItemCallback<QuestionEntity>() {
//    override fun areItemsTheSame(oldItem: QuestionEntity, newItem: QuestionEntity): Boolean {
//        return oldItem.ID == newItem.ID
//    }
//
//    override fun areContentsTheSame(oldItem: QuestionEntity, newItem: QuestionEntity): Boolean {
//        return oldItem.equals(newItem)
//    }
//}

class QuestionListItemDiffCallback : DiffUtil.ItemCallback<QuestionListItem>() {
    override fun areItemsTheSame(oldItem: QuestionListItem, newItem: QuestionListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: QuestionListItem, newItem: QuestionListItem): Boolean {
        return oldItem.equals(newItem)
    }
}*/