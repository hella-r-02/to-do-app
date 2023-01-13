package com.src.todo.presentation.addTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.src.todo.databinding.FragmentNoteBinding
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.addTask.viewModel.AddTaskViewModel

class NoteAddTaskFragment : Fragment() {
    private lateinit var binging: FragmentNoteBinding
    private lateinit var viewModel: AddTaskViewModel
    private val args: NoteAddTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binging = FragmentNoteBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getAddTaskViewModel()
        return binging.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note = args.taskNote
        if (note.isNotEmpty()) {
            binging.etNote.setText(note)
        }
        setOnClickListenerForDoneButton()
    }

    private fun setOnClickListenerForDoneButton() {
        binging.tvDone.setOnClickListener {
            viewModel.setNote(note = binging.etNote.text.toString())
            findNavController().popBackStack()
        }
    }
}