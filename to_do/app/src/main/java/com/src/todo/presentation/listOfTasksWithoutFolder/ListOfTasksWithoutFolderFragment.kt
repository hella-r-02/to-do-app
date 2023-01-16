package com.src.todo.presentation.listOfTasksWithoutFolder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.todo.databinding.FragmentListOfTasksWithoutFolderBinding
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.adapters.ListOfTasksAdapter
import com.src.todo.presentation.listOfTasksWithoutFolder.viewModel.ListOfTasksWithoutFolderViewModel
import com.src.todo.presentation.utils.State

class ListOfTasksWithoutFolderFragment : Fragment() {
    private lateinit var binding: FragmentListOfTasksWithoutFolderBinding
    private lateinit var viewModel: ListOfTasksWithoutFolderViewModel
    private var listOfTasks: java.util.ArrayList<TaskWithDate>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOfTasksWithoutFolderBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getListOfTasksWithoutFolderViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataLoadTasksState.observe(viewLifecycleOwner, this::parseState)
        viewModel.getTasks()
        setAdapterForListOfTasksRecyclerView()
        setOnClickListenerForBackButton()
        setOnClickListenerForAddTaskButton()
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
        setDataForListOfTasks(tasks)
    }

    private fun setAdapterForListOfTasksRecyclerView() {
        val adapter = ListOfTasksAdapter({ id -> setOnClickListenerForTask(id) },
            { id, position -> deleteTask(id, position) })
        adapter.submitList(null)
        val layoutInflater =
            GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvTasks.layoutManager = layoutInflater
        binding.rvTasks.adapter = adapter
    }

    private fun setDataForListOfTasks(tasksWithDate: List<TaskWithDate>?) {
        listOfTasks = tasksWithDate?.let { ArrayList(it) }
        val adapter = binding.rvTasks.adapter as ListOfTasksAdapter
        adapter.submitList(listOfTasks)
    }

    private fun setOnClickListenerForBackButton() {
        binding.clButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnClickListenerForTask(taskId: Long) {
        val direction =
            ListOfTasksWithoutFolderFragmentDirections.actionListOfTasksWithoutFolderFragmentToTaskFragment(
                taskId
            )
        findNavController().navigate(direction)
    }

    private fun setOnClickListenerForAddTaskButton() {
        binding.clAddTask.setOnClickListener {
            moveToAddTaskFragment()
        }
    }

    private fun moveToAddTaskFragment() {
        val direction =
            ListOfTasksWithoutFolderFragmentDirections.actionListOfTasksWithoutFolderFragmentToAddTaskFragment(
                0
            )
        findNavController().navigate(direction)
    }

    private fun deleteTask(id: Long, position: Int) {
        viewModel.deleteTask(id)
        listOfTasks?.removeAt(position)
        val adapter = binding.rvTasks.adapter as ListOfTasksAdapter
        adapter.notifyItemRemoved(position)
        adapter.notifyItemRangeChanged(position, listOfTasks?.size!!)
        if (listOfTasks?.get(position - 1) is TaskWithDate.DateTask) {
            if (position == (listOfTasks?.size) || listOfTasks?.get(position) is TaskWithDate.DateTask) {
                listOfTasks?.removeAt(position - 1)
                adapter.notifyItemRemoved(position - 1)
                adapter.notifyItemRangeChanged(position - 1, listOfTasks?.size!!)
            }
        }
    }
}