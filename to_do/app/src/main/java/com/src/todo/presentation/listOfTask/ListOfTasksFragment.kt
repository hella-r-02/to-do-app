package com.src.todo.presentation.listOfTask

import android.os.Bundle
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
        viewModel.liveDataTasks.observe(viewLifecycleOwner, this::getListOfTasks)
        binding.tvFolderName.text = name
        setOnClickListenerForBackButton()
        viewModel.getTasks(id)
    }

    private fun getListOfTasks(tasks: List<TaskWithDate>) {
        setAdapterForTasks(tasks)
    }

    private fun setAdapterForTasks(tasksWithDate: List<TaskWithDate>) {
        val adapter = ListOfTasksAdapter()
        adapter.submitList(tasksWithDate)
        adapter.setData(tasksWithDate)
        val layoutInflater = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvTasks.layoutManager = layoutInflater
        binding.rvTasks.adapter = adapter
    }

    private fun setOnClickListenerForBackButton() {
        binding.clButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}