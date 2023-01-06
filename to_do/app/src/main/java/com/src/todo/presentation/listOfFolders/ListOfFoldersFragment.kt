package com.src.todo.presentation.listOfFolders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.todo.R
import com.src.todo.databinding.FragmentListOfFoldersBinding
import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.adapters.ListOfFoldersAdapter
import com.src.todo.presentation.listOfFolders.viewModel.ListOfFoldersViewModel

class ListOfFoldersFragment : Fragment() {
    private lateinit var binding: FragmentListOfFoldersBinding
    private lateinit var viewModel: ListOfFoldersViewModel

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
        viewModel.liveDataFolders.observe(viewLifecycleOwner, this::getFolders)
        viewModel.getFolders()
    }

    private fun getFolders(folders: List<FolderWithCountOfTasks>) {
        setDataForListOfFoldersAdapter(folders)
        var countOfTasks: Long = 0
        folders.forEach {
            countOfTasks += it.count
        }
        binding.tvCountOfTasks.text = countOfTasks.toString()
    }

    private fun setDataForListOfFoldersAdapter(folders: List<FolderWithCountOfTasks>) {
        val adapter = ListOfFoldersAdapter { id, name -> openFolder(id, name) }
        adapter.submitList(folders)
        binding.rvFolders.layoutManager =
            GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvFolders.adapter = adapter
    }

    private fun openFolder(id: Long, name: String) {
        val direction =
            ListOfFoldersFragmentDirections.actionListOfFoldersFragmentToListOfTasksFragment(
                id,
                name
            )
        findNavController().navigate(direction)
    }
}