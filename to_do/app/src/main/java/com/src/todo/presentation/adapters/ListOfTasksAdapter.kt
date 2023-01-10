package com.src.todo.presentation.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.src.todo.R
import com.src.todo.databinding.ViewHolderDateBinding
import com.src.todo.databinding.ViewHolderTaskBinding
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.presentation.utils.ConvectorDateToString
import com.src.todo.presentation.utils.SHORT_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class ListOfTasksAdapter(private val onClickTask: (taskId: Long) -> Unit) :
    ListAdapter<TaskWithDate, ListOfTasksAdapter.DataViewHolder>(TaskWithDateDiffCallback()) {
    private lateinit var binding: ViewBinding
    private val adapterData = mutableListOf<TaskWithDate>()

    class DataViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        private fun onBindTask(task: TaskWithDate.Task, onClickTask: (taskId: Long) -> Unit) {
            val bindingTask = binding as ViewHolderTaskBinding
            bindingTask.tvTaskName.text = task.name
            if (task.date == null) {
                bindingTask.ivCalendar.visibility = View.GONE
                bindingTask.tvDate.visibility = View.GONE
            } else {
                val now = Calendar.getInstance(TimeZone.getDefault()).time
                val colorId = if (now.time > task.date.time) {
                    R.color.main_color
                } else {
                    R.color.white_60_per
                }
                ImageViewCompat.setImageTintList(
                    bindingTask.ivCalendar,
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            bindingTask.ivCalendar.context,
                            colorId
                        )
                    )
                )
                bindingTask.tvDate.setTextColor(ContextCompat.getColor(context, colorId))
                bindingTask.tvDate.text = SimpleDateFormat(SHORT_DATE_FORMAT).format(task.date)
            }
           itemView.setOnClickListener {
               onClickTask(task.id)
           }
        }

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        private fun onBindDate(dateTask: TaskWithDate.DateTask) {
            val bindingDate = binding as ViewHolderDateBinding
            bindingDate.tvDate.text =
                ConvectorDateToString().convectDateToString(dateTask.date, itemView.context)
        }

        fun onBind(taskWithDate: TaskWithDate, onClickTask: (taskId: Long) -> Unit) {
            when (taskWithDate) {
                is TaskWithDate.Task -> onBindTask(taskWithDate, onClickTask)
                is TaskWithDate.DateTask -> onBindDate(taskWithDate)
            }
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        when (viewType) {
            TYPE_TASK -> {
                binding = ViewHolderTaskBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            TYPE_DATE -> {
                binding = ViewHolderDateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
        }
        return DataViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is TaskWithDate.Task -> TYPE_TASK
            is TaskWithDate.DateTask -> TYPE_DATE
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, onClickTask)
    }

    fun setData(data: List<TaskWithDate>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }

    companion object {
        private const val TYPE_TASK = 0
        private const val TYPE_DATE = 1
    }
}
