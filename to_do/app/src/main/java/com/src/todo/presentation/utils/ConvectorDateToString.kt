package com.src.todo.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import com.src.todo.R
import java.text.SimpleDateFormat
import java.util.*

class ConvectorDateToString {
    @SuppressLint("SimpleDateFormat")
    fun convectDateToString(date: Date?, context: Context): String {
        if (date == null) {
            return context.getString(R.string.indefinitely)
        }
        val now = Calendar.getInstance(TimeZone.getDefault())
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.time = date
        if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)
            ) {
                val dateString = SimpleDateFormat(DATE_FORMAT).format(date)
                return "${context.getString(R.string.today)}, $dateString"
            }
            now.add(Calendar.DATE, 1)
            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)
            ) {
                val dateString = SimpleDateFormat(DATE_FORMAT).format(date)

                return "${context.getString(R.string.tomorrow)}, $dateString"
            }
            return SimpleDateFormat(DATE_FORMAT).format(date)
        }
        return SimpleDateFormat(DATE_FORMAT_WITH_YEAR).format(date)
    }
}