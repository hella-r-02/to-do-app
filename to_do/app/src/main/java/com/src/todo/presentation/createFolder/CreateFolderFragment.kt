package com.src.todo.presentation.createFolder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.src.todo.R
import com.src.todo.databinding.FragmentCreateFolderBinding
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.createFolder.viewModel.CreateFolderViewModel
import com.src.todo.presentation.utils.State

class CreateFolderFragment : Fragment() {
    private lateinit var binding: FragmentCreateFolderBinding
    private lateinit var viewModel: CreateFolderViewModel
    private var folderName: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateFolderBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getCreateFolderViewModel()
        viewModel.liveDataInsertFoldersState.observe(viewLifecycleOwner, this::parseState)
        return binding.root
    }

    private fun parseState(state: State<Long>) {
        when (state) {
            is State.LoadingState -> {
                Log.d("Fragment", "Load")
            }
            is State.SuccessState -> {
                navigateToFolderFragment(state.data)
            }
            else -> {}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnFocusChangeListener()
        setFocusForEditText()
        setOnClickListenerForBackButton()
    }

    private fun setFocusForEditText() {
        binding.etFolderName.setText(resources.getString(R.string.untitled))
        if (!binding.etFolderName.hasFocus()) {
            binding.etFolderName.requestFocus()
        }
        val inputManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun setOnFocusChangeListener() {
        binding.etFolderName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etFolderName.clearFocus()
                folderName = requireContext().resources.getString(R.string.untitled)
                if (binding.etFolderName.text.toString().isNotEmpty()) {
                    folderName = binding.etFolderName.text.toString()
                }
                viewModel.createFolder(folderName)
                return@setOnEditorActionListener false
            } else {
                return@setOnEditorActionListener true
            }
        }
    }

    private fun navigateToFolderFragment(id: Long) {
        viewModel.setDefaultValue()
        val direction =
            CreateFolderFragmentDirections.actionCreateFolderFragmentToListOfTasksFragment(
                id,
                folderName
            )
        findNavController().navigate(direction)
    }

    private fun setOnClickListenerForBackButton() {
        binding.clButtonBack.setOnClickListener {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etFolderName.windowToken, 0)
            findNavController().popBackStack()
        }
    }
}