package com.src.todo.presentation.addTask

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.src.todo.R
import com.src.todo.databinding.FragmentAddTaskBinding
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.addTask.viewModel.AddTaskViewModel
import com.src.todo.presentation.dialog.CalendarBottomSheetDialogFragment
import com.src.todo.presentation.dialog.DateBottomSheetDialogFragment
import com.src.todo.presentation.dialog.DialogEnum
import com.src.todo.presentation.task.TaskFragment
import com.src.todo.presentation.utils.ConvectorDateToString
import java.util.*

class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var viewModel: AddTaskViewModel
    private lateinit var dateDialog: DateBottomSheetDialogFragment
    private lateinit var calendarDialog: CalendarBottomSheetDialogFragment
    private val args: AddTaskFragmentArgs by navArgs()
    private var folderId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getAddTaskViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        folderId = args.folderId
        viewModel.liveDataNote.observe(viewLifecycleOwner, this::setTextForNote)
        viewModel.liveDataDate.observe(viewLifecycleOwner, this::setDateFromViewModel)
        setOnClickListenerForCalendarIcon()
        setOnClickListenerForRemoveDateIcon()
        setOnClickListenerForCompleteIcon()
        setOnClickListenerForNoteButton()
        setOnClickListenerForRemoveNoteIcon()
        setOnClickListenerForBackButton()
    }

    private fun setTextForNote(note: String?) {
        with(binding.tvNote) {
            if (note != null && note.isNotEmpty()) {
                text = note
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.ivRemoveNote.visibility = View.VISIBLE
            } else {
                text = getString(R.string.no_note)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
                binding.ivRemoveNote.visibility = View.GONE
            }
        }
    }

    private fun setDateFromViewModel(date: Date?) {
        with(binding.tvDate) {
            if (date != null) {
                text = ConvectorDateToString().convectDateToString(date, requireContext())
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.ivRemoveDate.visibility = View.VISIBLE
            } else {
                text = getString(R.string.indefinitely)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
                binding.ivRemoveDate.visibility = View.GONE
            }
        }
    }

    private fun setOnClickListenerForCalendarIcon() {
        binding.tvDate.setOnClickListener {
            showDateDialog()
        }
    }

    private fun showDateDialog() {
        dateDialog = DateBottomSheetDialogFragment({ date, dialogType ->
            setDateForTask(
                date,
                dialogType
            )
        },
            { showCalendarDialog() }
        )
        dateDialog.show(parentFragmentManager, TaskFragment.DATE_DIALOG)
    }

    private fun setDateForTask(date: Date, dialogType: DialogEnum) {
        viewModel.setDate(date)
        setTextForTextViewDate(date)
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
        calendarDialog.show(parentFragmentManager, TaskFragment.CALENDAR_DIALOG)
    }

    private fun setOnClickListenerForBackButtonForCalendarDialog() {
        calendarDialog.dismiss()
        dateDialog.show(parentFragmentManager, TaskFragment.DATE_DIALOG)
    }

    private fun createTask() {
        val name = binding.etTaskName.text.toString()
        if (name.isNotEmpty()) {
            viewModel.insertTask(name = name, folderId = folderId)
        }
        navigateToBack()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    fun setTextForTextViewDate(date: Date?) {
        with(binding.tvDate) {
            text = ConvectorDateToString().convectDateToString(date, requireContext())
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.ivRemoveDate.visibility = View.VISIBLE
        }
    }

    private fun setOnClickListenerForBackButton() {
        binding.clButtonBack.setOnClickListener {
            navigateToBack()
        }
    }

    private fun navigateToBack() {
        findNavController().popBackStack()
    }

    private fun setOnClickListenerForCompleteIcon() {
        binding.ivSubmit.setOnClickListener {
            createTask()
        }
    }

    private fun setOnClickListenerForNoteButton() {
        binding.tvNote.setOnClickListener {
            showNoteFragment()
        }
    }

    private fun showNoteFragment() {
        var note = ""
        if (viewModel.getNote() != null) {
            note = viewModel.getNote()!!
        }
        val direction = AddTaskFragmentDirections.actionAddTaskFragmentToNoteAddTaskFragment(note)
        findNavController().navigate(direction)
    }

    private fun setOnClickListenerForRemoveDateIcon() {
        binding.ivRemoveDate.setOnClickListener {
            with(binding.tvDate) {
                text = getString(R.string.indefinitely)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
            }
            binding.ivRemoveDate.visibility = View.GONE
        }
    }

    private fun setOnClickListenerForRemoveNoteIcon() {
        binding.ivRemoveNote.setOnClickListener {
            with(binding.tvNote) {
                text = getString(R.string.no_note)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
                binding.ivRemoveNote.visibility = View.GONE
            }
        }
    }
}