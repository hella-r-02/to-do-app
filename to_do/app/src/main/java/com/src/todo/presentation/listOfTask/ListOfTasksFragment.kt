package com.src.todo.presentation.listOfTask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.todo.databinding.FragmentListOfTasksBinding
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.adapters.ListOfTasksAdapter
import com.src.todo.presentation.listOfTask.viewModel.ListOfTasksViewModel
import com.src.todo.presentation.utils.State

class ListOfTasksFragment : Fragment() {
    private lateinit var binding: FragmentListOfTasksBinding
    private lateinit var viewModel: ListOfTasksViewModel
    private val args: ListOfTasksFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOfTasksBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getListOfTasksViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.folderId
        val name = args.folderName
        viewModel.liveDataLoadTasksState.observe(viewLifecycleOwner, this::parseState)
        binding.tvFolderName.text = name
        setOnClickListenerForBackButton()
        viewModel.getTasks(id)
    }

    private fun parseState(state: State<List<TaskWithDate>>) {
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

    private fun setData(tasks: List<TaskWithDate>?) {
        setAdapterForTasks(tasks)
    }

    private fun setAdapterForTasks(tasksWithDate: List<TaskWithDate>?) {
        if (tasksWithDate != null) {
            val adapter = ListOfTasksAdapter { id -> setOnClickListenerForTask(id) }
            adapter.submitList(tasksWithDate)
            adapter.setData(tasksWithDate)
            val layoutInflater =
                GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
            binding.rvTasks.layoutManager = layoutInflater
            binding.rvTasks.adapter = adapter
        }
    }

    private fun setOnClickListenerForBackButton() {
        binding.clButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnClickListenerForTask(taskId: Long) {
        val direction =
            ListOfTasksFragmentDirections.actionListOfTasksFragmentToTaskFragment(taskId)
        findNavController().navigate(direction)
    }
}