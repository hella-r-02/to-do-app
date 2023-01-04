package com.src.todo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.todo.databinding.FragmentListOfTasksBinding
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.presentation.adapters.ListOfTasksAdapter
import java.util.*

class ListOfTasksFragment : Fragment() {
    private lateinit var binding: FragmentListOfTasksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOfTasksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.add(Calendar.DAY_OF_MONTH, -1)
//        val tasks = listOf<TaskWithDate>(
//            TaskWithDate.DateTask(date = "3 января"),
//            TaskWithDate.Task(
//                id = 1,
//                name = "name",
//                date = calendar.time,
//                folder = null,
//                note = null,
//                repeating = null
//            ),
//            TaskWithDate.DateTask(date = "3 января"),
//            TaskWithDate.DateTask(date = "3 января")
//        )
      //  setAdapterForTasks(tasks)
    }

    private fun setAdapterForTasks(tasksWithDate: List<TaskWithDate>) {
        val adapter = ListOfTasksAdapter()
        adapter.submitList(tasksWithDate)
        adapter.setData(tasksWithDate)
        val layoutInflater = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvTasks.layoutManager = layoutInflater
        binding.rvTasks.adapter = adapter
    }
}