package com.example.hello_there.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Generates 1-hour slots from 09:00 AM to 05:00 PM
val ALL_TIME_SLOTS = buildList {
    for (hour in 9 until 17) {
        val startHour = if (hour < 10) "0$hour:00" else "$hour:00"
        val endHour = if (hour + 1 < 10) "0${hour + 1}:00" else "${hour + 1}:00"
        val startSlot =
            if (hour < 12) "$startHour AM" else (if (hour == 12) "$startHour PM" else "${if (hour > 12) "0${hour - 12}:00" else startHour} PM")
        val endSlot =
            if (hour + 1 < 12) "$endHour AM" else (if (hour + 1 == 12) "$endHour PM" else "${if (hour + 1 > 12) "0${hour + 1 - 12}:00" else endHour} PM")
        this += "$startSlot - $endSlot"
    }
}

// Model for a class with timeslot and owner/teacher
data class ClassInfo(val name: String, val timeSlot: String, val teacher: String)

object ClassRepository {
    // For demo, persists only in-memory; could add DataStore/Room if needed
    private val _classes = MutableStateFlow<List<ClassInfo>>(
        listOf(
            ClassInfo("Android Dev 101", "09:00 AM - 10:00 AM", "teacher1@example.com"),
            ClassInfo("Data Structures", "10:00 AM - 11:00 AM", "teacher2@example.com")
        )
    )
    val classes: StateFlow<List<ClassInfo>> = _classes

    fun addClass(classInfo: ClassInfo) {
        _classes.value = _classes.value + classInfo
    }

    fun clearAll() {
        _classes.value = emptyList()
    }
}
