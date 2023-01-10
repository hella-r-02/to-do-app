package com.src.todo.presentation.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.src.todo.R
import com.src.todo.databinding.FragmentTaskBinding
import com.src.todo.domain.model.Task
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.task.viewModel.TaskViewModel
import com.src.todo.presentation.utils.ConvectorDateToString
import com.src.todo.presentation.utils.State

class TaskFragment : Fragment() {
    private lateinit var binging: FragmentTaskBinding
    private lateinit var viewModel: TaskViewModel
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
        binging.tvTaskName.text = task.name
        with(binging.tvDate) {
            if (task.date != null) {
                text = ConvectorDateToString().convectDateToString(task.date, requireContext())
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                text = getString(R.string.indefinitely)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
            }
        }
        with(binging.tvNote) {
            if (task.note != null) {
                text = task.note
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                text = getString(R.string.no_note)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white_60_per))
            }
        }
    }

    private fun setOnClickListenerForBackButton() {
        binging.clButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}