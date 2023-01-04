package com.src.todo.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.src.todo.domain.model.TaskWithDate

class TaskWithDateDiffCallback : DiffUtil.ItemCallback<TaskWithDate>() {
    override fun areItemsTheSame(oldItem: TaskWithDate, newItem: TaskWithDate): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TaskWithDate, newItem: TaskWithDate): Boolean {
        return oldItem == newItem
    }
}
