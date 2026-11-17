package com.example.welcome.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.WordItem
import com.example.welcome.databinding.ItemWordBinding

class WordAdapter(
    private val onDelete: (WordItem) -> Unit
) : ListAdapter<WordItem, WordAdapter.WordViewHolder>(WordDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WordViewHolder(
        private val binding: ItemWordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.deleteButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) onDelete(getItem(position))
            }
        }

        fun bind(item: WordItem) = with(binding) {
            wordText.text = item.word
            translationText.text = item.translation
            exampleText.text = item.example
        }
    }
}

private object WordDiffCallback : DiffUtil.ItemCallback<WordItem>() {
    override fun areItemsTheSame(oldItem: WordItem, newItem: WordItem) =
        oldItem.word == newItem.word

    override fun areContentsTheSame(oldItem: WordItem, newItem: WordItem) =
        oldItem == newItem
}