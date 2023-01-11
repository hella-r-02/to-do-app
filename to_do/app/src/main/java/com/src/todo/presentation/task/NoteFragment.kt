package com.src.todo.presentation.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.src.todo.databinding.FragmentNoteBinding
import com.src.todo.presentation.MainActivity
import com.src.todo.presentation.task.viewModel.TaskViewModel

class NoteFragment : Fragment() {
    private lateinit var binging: FragmentNoteBinding
    private lateinit var viewModel: TaskViewModel
    private val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binging = FragmentNoteBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getTaskViewModel()
        return binging.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note = args.taskNote
        setData(note)
    }

    private fun setData(note: String?) {
        if (note != null && note.isNotEmpty()) {
            binging.etNote.setText(note)
        }
        setOnClickListenerForDoneButton()
    }

    private fun setOnClickListenerForDoneButton() {
        binging.tvDone.setOnClickListener {
            viewModel.updateNote(note = binging.etNote.text.toString())
            findNavController().popBackStack()
        }
    }
}