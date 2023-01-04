package com.src.todo.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.src.todo.databinding.ViewHolderFolderBinding
import com.src.todo.domain.model.FolderWithCountOfTasks

class ListOfFoldersAdapter :
    ListAdapter<FolderWithCountOfTasks, ListOfFoldersAdapter.DataViewHolder>(
        ListOfFoldersDiffCallback()
    ) {
    private lateinit var binding: ViewHolderFolderBinding

    class DataViewHolder(private val binding: ViewHolderFolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun onBind(folderWithCountOfTasks: FolderWithCountOfTasks) {
            binding.tvCountOfTasks.text = folderWithCountOfTasks.count.toString()
            binding.tvFolderName.text = folderWithCountOfTasks.folder.name
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = ViewHolderFolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }
}

class ListOfFoldersDiffCallback : DiffUtil.ItemCallback<FolderWithCountOfTasks>() {
    override fun areItemsTheSame(
        oldItem: FolderWithCountOfTasks,
        newItem: FolderWithCountOfTasks
    ): Boolean {
        return oldItem.folder.id == newItem.folder.id
    }

    override fun areContentsTheSame(
        oldItem: FolderWithCountOfTasks,
        newItem: FolderWithCountOfTasks
    ): Boolean {
        return oldItem.folder == newItem.folder
    }

}