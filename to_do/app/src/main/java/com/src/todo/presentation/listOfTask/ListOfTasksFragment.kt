package com.src.todo.presentation.listOfTask

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.todo.R
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
    private var folderId: Long = 0
    private var listOfTasks: java.util.ArrayList<TaskWithDate>? = null

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
        folderId = args.folderId
        val name = args.folderName
        viewModel.liveDataLoadTasksState.observe(viewLifecycleOwner, this::parseState)
        binding.etFolderName.setText(name)
        setAdapterForListOfTasksRecyclerView()
        setOnClickListenerForBackButton()
        setOnClickListenerForAddTaskButton()
        setOnFocusChangeListenerForFolderName()
        viewModel.getTasks(folderId)
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
            { id -> deleteTask(id) })
        adapter.submitList(null)
        val layoutInflater =
            GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvTasks.layoutManager = layoutInflater
        binding.rvTasks.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
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
            ListOfTasksFragmentDirections.actionListOfTasksFragmentToTaskFragment(taskId)
        findNavController().navigate(direction)
    }

    private fun setOnClickListenerForAddTaskButton() {
        binding.clAddTask.setOnClickListener {
            moveToAddTaskFragment()
        }
    }

    private fun moveToAddTaskFragment() {
        val direction =
            ListOfTasksFragmentDirections.actionListOfTasksFragmentToAddTaskFragment(folderId)
        findNavController().navigate(direction)
    }

    private fun deleteTask(id: Long) {
        viewModel.deleteTask(id, folderId)
    }

    private fun setOnFocusChangeListenerForFolderName() {
        binding.etFolderName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etFolderName.clearFocus()
                if (binding.etFolderName.text.toString().isNotEmpty()) {
                    val name = binding.etFolderName.text.toString().ifEmpty {
                        getString(R.string.untitled)
                    }
                    viewModel.updateFolderName(name, folderId)
                }
                return@setOnEditorActionListener false
            } else {
                return@setOnEditorActionListener true
            }
        }
    }
}