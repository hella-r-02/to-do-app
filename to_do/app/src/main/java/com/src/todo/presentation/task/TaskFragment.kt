package com.src.todo.presentation.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.src.todo.R
import com.src.todo.databinding.FragmentTaskBinding
import com.src.todo.domain.model.Task
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.dialog.CalendarBottomSheetDialogFragment
import com.src.todo.presentation.dialog.DateBottomSheetDialogFragment
import com.src.todo.presentation.dialog.DialogEnum
import com.src.todo.presentation.task.viewModel.TaskViewModel
import com.src.todo.presentation.utils.ConvectorDateToString
import com.src.todo.presentation.utils.State
import java.util.*


class TaskFragment : Fragment() {
    private lateinit var binging: FragmentTaskBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var task: Task
    private lateinit var dateDialog: DateBottomSheetDialogFragment
    private lateinit var calendarDialog: CalendarBottomSheetDialogFragment
    private val args: TaskFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binging = FragmentTaskBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getTaskViewModel()
        return binging.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataLoadTaskState.observe(viewLifecycleOwner, this::parseState)
        val id = args.taskId
        viewModel.getTask(id)
        setOnClickListenerForBackButton()
        setOnFocusChangeListener()
        showDateDialog()
        setOnClickListenerForRemoveNoteIcon()
        setOnClickListenerForRemoveDateIcon()
    }

    private fun parseState(state: State<Task>) {
        when (state) {
            is State.LoadingState -> {
                Log.d("Fragment", "Load")
            }
            is State.SuccessState -> {
                setData(state.data)
            }
            else -> {}
        }
    }

    private fun setData(task: Task) {
        this.task = task
        binging.tvTaskName.text = task.name
        binging.etTaskName.setText(task.name)
        with(binging.tvDate) {
            if (task.date != null) {
                text = ConvectorDateToString().convectDateToString(task.date, requireContext())
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binging.ivRemoveDate.visibility = View.VISIBLE
            } else {
                text = getString(R.string.indefinitely)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
                binging.ivRemoveDate.visibility = View.GONE
            }
        }
        with(binging.tvNote) {
            if (task.note != null && task.note!!.isNotEmpty()) {
                text = task.note
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binging.ivRemoveNote.visibility = View.VISIBLE
            } else {
                text = getString(R.string.no_note)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
                binging.ivRemoveNote.visibility = View.GONE
            }
        }
        setOnClickListenerForNoteButton()
    }

    private fun setOnClickListenerForBackButton() {
        binging.clButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnFocusChangeListener() {
        binging.etTaskName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binging.etTaskName.clearFocus()
                if (binging.etTaskName.text.toString().isNotEmpty()) {
                    task.name = binging.etTaskName.text.toString()
                    viewModel.updateTask(task)
                }
                return@setOnEditorActionListener false
            } else {
                return@setOnEditorActionListener true
            }
        }
    }

    private fun setOnClickListenerForBackButtonForCalendarDialog() {
        calendarDialog.dismiss()
        dateDialog.show(parentFragmentManager, DATE_DIALOG)
    }

    private fun showDateDialog() {
        binging.tvDate.setOnClickListener {
            dateDialog = DateBottomSheetDialogFragment({ date, dialogType ->
                setDateForTask(
                    date,
                    dialogType
                )
            },
                { showCalendarDialog() }
            )
            dateDialog.show(parentFragmentManager, DATE_DIALOG)
        }
    }

    private fun setDateForTask(date: Date, dialogType: DialogEnum) {
        task.date = date
        with(binging.tvDate) {
            text = ConvectorDateToString().convectDateToString(task.date, requireContext())
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binging.ivRemoveDate.visibility = View.VISIBLE
        }
        viewModel.updateTask(task)
        if (dialogType == DialogEnum.DATE) {
            dateDialog.dismiss()
        } else {
            calendarDialog.dismiss()
        }
    }

    private fun showCalendarDialog() {
        calendarDialog = CalendarBottomSheetDialogFragment({ date, dialogType ->
            setDateForTask(
                date,
                dialogType
            )
        }, { setOnClickListenerForBackButtonForCalendarDialog() })
        dateDialog.dismiss()
        calendarDialog.show(parentFragmentManager, CALENDAR_DIALOG)
    }

    private fun setOnClickListenerForNoteButton() {
        binging.tvNote.setOnClickListener {
            showNoteFragment(task)
        }
    }

    private fun showNoteFragment(task: Task) {
        val note = if (task.note != null) task.note!! else ""
        val direction = TaskFragmentDirections.actionTaskFragmentToNoteFragment(note)
        findNavController().navigate(direction)
    }

    private fun setOnClickListenerForRemoveDateIcon() {
        binging.ivRemoveDate.setOnClickListener {
            task.date = null
            viewModel.updateTask(task)
            with(binging.tvDate) {
                text = getString(R.string.indefinitely)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
            }
            binging.ivRemoveDate.visibility = View.GONE
        }
    }

    private fun setOnClickListenerForRemoveNoteIcon() {
        binging.ivRemoveNote.setOnClickListener {
            task.note = null
            viewModel.updateTask(task)
            with(binging.tvNote) {
                text = getString(R.string.no_note)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
                binging.ivRemoveNote.visibility = View.GONE
            }
        }
    }

    companion object {
        const val DATE_DIALOG = "date_dialog"
        const val CALENDAR_DIALOG = "calendar_dialog"
    }
}