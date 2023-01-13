package com.src.todo.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.src.todo.R
import com.src.todo.databinding.FragmentDateBottomSheetDialogBinding
import com.src.todo.presentation.utils.DAY_OF_WEEK_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class DateBottomSheetDialogFragment(
    private val setDateForTask: (date: Date, dialogType: DialogEnum) -> Unit,
    private val showCalendarDialog: () -> Unit
) :
    BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDateBottomSheetDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDateBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.custom_bottom_sheet_dialog_theme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        val format = SimpleDateFormat(DAY_OF_WEEK_FORMAT, Locale("ru"))
        val calendar = Calendar.getInstance()
        binding.tvTodayDayOfWeek.text = format.format(calendar.time)
        calendar.add(Calendar.DATE, 1)
        binding.tvTomorrowDayOfWeek.text = format.format(calendar.time)
        setOnClickListenerForTodayButton()
        setOnClickListenerForTomorrowButton()
        setOnClickListenerForSelectDateButton()
    }

    private fun setOnClickListenerForTodayButton() {
        binding.clToday.setOnClickListener {
            val calendar = Calendar.getInstance()
            setDateForTask(calendar.time, DialogEnum.DATE)
        }
    }

    private fun setOnClickListenerForTomorrowButton() {
        binding.clTomorrow.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 1)
            setDateForTask(calendar.time, DialogEnum.DATE)
        }
    }

    private fun setOnClickListenerForSelectDateButton() {
        binding.clSelectDate.setOnClickListener {
            showCalendarDialog()
        }
    }
}