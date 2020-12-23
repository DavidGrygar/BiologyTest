package android.example.biologytest.adapters

import android.example.biologytest.databinding.TopicGroupRowLayoutBinding
import android.example.biologytest.model.entities.TopicGroupEntity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TopicGroupListAdapter() : ListAdapter<TopicGroupEntity, TopicGroupListAdapter.ViewHolder>(TopicGroupEntityDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicGroupListAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TopicGroupListAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: TopicGroupRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(/*clickListener: BookListener,*/ item: TopicGroupEntity) {
            binding.topicGroup = item
            //binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TopicGroupRowLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class TopicGroupEntityDiffCallback : DiffUtil.ItemCallback<TopicGroupEntity>() {
    override fun areItemsTheSame(oldItem: TopicGroupEntity, newItem: TopicGroupEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TopicGroupEntity, newItem: TopicGroupEntity): Boolean {
        return oldItem == newItem
    }
}