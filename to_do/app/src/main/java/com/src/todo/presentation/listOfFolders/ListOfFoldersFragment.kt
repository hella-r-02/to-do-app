package com.src.todo.presentation.listOfFolders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.src.todo.databinding.FragmentListOfFoldersBinding
import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.adapters.ListOfFoldersAdapter
import com.src.todo.presentation.adapters.SwipeToDeleteCallback
import com.src.todo.presentation.listOfFolders.viewModel.ListOfFoldersViewModel
import com.src.todo.presentation.utils.State

class ListOfFoldersFragment : Fragment() {
    private lateinit var binding: FragmentListOfFoldersBinding
    private lateinit var viewModel: ListOfFoldersViewModel
    private lateinit var listOfFolders: java.util.ArrayList<FolderWithCountOfTasks>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOfFoldersBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getListOfFoldersViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataLoadFoldersState.observe(viewLifecycleOwner, this::parseState)
        viewModel.liveDataCountOfTasksWithoutFolderState.observe(
            viewLifecycleOwner,
            this::parseCountOfTasksWithoutFolder
        )
        viewModel.getFolders()
        viewModel.getCountOfTasksWithoutFolders()
        setOnClickListenerForAddFolderButton()
        setOnClickListenerForListOfTasksButton()
    }

    private fun parseCountOfTasksWithoutFolder(state: State<Long>) {
        when (state) {
            is State.LoadingState -> {
                Log.d("Fragment", "Load")
            }
            is State.SuccessState -> {
                binding.tvCountOfTasks.text = state.data.toString()
            }
            else -> {}
        }
    }

    private fun parseState(state: State<List<FolderWithCountOfTasks>>) {
        when (state) {
            is State.LoadingState -> {
                Log.d("Fragment", "Load")
            }
            is State.SuccessState -> {
                loadData(state.data)
            }
            else -> {}
        }
    }

    private fun loadData(folders: List<FolderWithCountOfTasks>) {
        setDataForListOfFoldersAdapter(folders)
//        var countOfTasks: Long = 0
//        folders.forEach {
//            countOfTasks += it.count
//        }
//        binding.tvCountOfTasks.text = countOfTasks.toString()
    }

    private fun setDataForListOfFoldersAdapter(folders: List<FolderWithCountOfTasks>) {
        this.listOfFolders = ArrayList(folders)
        val adapter = ListOfFoldersAdapter { id, name -> openFolder(id, name) }
        adapter.submitList(listOfFolders)
        binding.rvFolders.layoutManager =
            GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvFolders.adapter = adapter
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val listOfFoldersAdapter = binding.rvFolders.adapter as ListOfFoldersAdapter
                val folder = listOfFoldersAdapter.currentList[viewHolder.adapterPosition]
                listOfFolders.removeAt(viewHolder.adapterPosition)
                listOfFoldersAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                viewModel.deleteFolder(folder)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvFolders)
    }

    private fun openFolder(id: Long, name: String) {
        val direction =
            ListOfFoldersFragmentDirections.actionListOfFoldersFragmentToListOfTasksFragment(
                id,
                name
            )
        findNavController().navigate(direction)
    }

    private fun setOnClickListenerForAddFolderButton() {
        binding.clAddFolder.setOnClickListener {
            val direction =
                ListOfFoldersFragmentDirections.actionListOfFoldersFragmentToCreateFolderFragment()
            findNavController().navigate(direction)
        }
    }

    private fun setOnClickListenerForListOfTasksButton() {
        binding.clListOfTasks.setOnClickListener {
            navigateToListOfTasksWithoutFolderFragment()
        }
    }

    private fun navigateToListOfTasksWithoutFolderFragment() {
        val direction =
            ListOfFoldersFragmentDirections.actionListOfFoldersFragmentToListOfTasksWithoutFolderFragment()
        findNavController().navigate(direction)
    }
}