package com.src.todo.presentation.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
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

class ListOfTasksAdapter(
    private val onClickTask: (taskId: Long) -> Unit,
    private val deleteTask: (id: Long) -> Unit
) :
    ListAdapter<TaskWithDate, ListOfTasksAdapter.DataViewHolder>(TaskWithDateDiffCallback()) {
    private lateinit var binding: ViewBinding
    private val adapterData = mutableListOf<TaskWithDate>()

    class DataViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        private fun onBindTask(
            task: TaskWithDate.Task,
            onClickTask: (taskId: Long) -> Unit,
            deleteTask: (id: Long) -> Unit
        ) {
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
            binding.ivComplete.setOnClickListener {
                val animationIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                val animationOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                animationOut.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        binding.ivComplete.setImageResource(R.drawable.ic_baseline_check_24)
                        binding.ivComplete.setBackgroundResource(R.drawable.task_complete_background)
                        animationIn.setAnimationListener(object : AnimationListener {
                            override fun onAnimationStart(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                deleteTask(task.id)
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        }
                        )
                        binding.ivComplete.startAnimation(animationIn)
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
                binding.ivComplete.startAnimation(animationOut)
            }
            binding.ivTrash.setOnClickListener {
                deleteTask(task.id)
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

        fun onBind(
            taskWithDate: TaskWithDate,
            onClickTask: (taskId: Long) -> Unit,
            deleteTask: (id: Long) -> Unit
        ) {
            when (taskWithDate) {
                is TaskWithDate.Task -> onBindTask(taskWithDate, onClickTask, deleteTask)
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
        if (adapterData.size <= position) {
            return NO_TYPE
        }
        return when (adapterData[position]) {
            is TaskWithDate.Task -> TYPE_TASK
            is TaskWithDate.DateTask -> TYPE_DATE
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, onClickTask, deleteTask)
    }

    override fun submitList(list: List<TaskWithDate>?) {
        adapterData.apply {
            clear()
            list?.let { addAll(it) }
        }
        super.submitList(list)
    }

    companion object {
        private const val TYPE_TASK = 0
        private const val TYPE_DATE = 1
        private const val NO_TYPE = 2
    }
}
