package com.src.todo.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.src.todo.R
import com.src.todo.databinding.FragmentCalendarBottomSheetDialogBinding
import java.util.*


class CalendarBottomSheetDialogFragment(
    private val setDateForTask: (date: Date, dialogType: DialogEnum) -> Unit,
    private val showDateDialog: () -> Unit
) :
    BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCalendarBottomSheetDialogBinding
    private var dateTimInMillis: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.custom_bottom_sheet_dialog_theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBottomSheetDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListenerForCalendar()
        setOnClickListenerForSetButton()
        setOnClickListenerForBackButton()
    }

    private fun setOnClickListenerForSetButton() {
        binding.tvSet.setOnClickListener {
            val date = Date(dateTimInMillis)
            setDateForTask(date, DialogEnum.CALENDAR)
        }
    }

    private fun setListenerForCalendar() {
        binding.clTaskDate.setOnDateChangeListener { _, year, month, day ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            dateTimInMillis = calendar.timeInMillis
        }
    }

    private fun setOnClickListenerForBackButton() {
        binding.ivBack.setOnClickListener {
            showDateDialog()
        }
    }
}