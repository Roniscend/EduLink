package com.example.hello_there.screens.classes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hello_there.data.ClassInfo
import com.example.hello_there.data.ClassRepository
import androidx.lifecycle.compose.collectAsStateWithLifecycle

enum class UserRole { Teacher, Student }

@Composable
fun ClassesScreen(
    role: UserRole = UserRole.Student,
    teacherEmail: String = "teacher1@example.com"
) {
    val allClasses by ClassRepository.classes.collectAsStateWithLifecycle()
    val myClasses =
        if (role == UserRole.Teacher) allClasses.filter { it.teacher == teacherEmail } else allClasses

    var newClassName by remember { mutableStateOf("") }
    var selectedTimeSlot by remember { mutableStateOf("") }
    var timeSlotDialogOpen by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    // 1-hour slots, always shown in picker!
    val allSlots = listOf(
        "09:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM", "12:00 PM - 01:00 PM",
        "01:00 PM - 02:00 PM", "02:00 PM - 03:00 PM", "03:00 PM - 04:00 PM", "04:00 PM - 05:00 PM"
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            if (role == UserRole.Teacher) {
                // ---- Class Addition Form ----
                Text("Add New Class", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = newClassName,
                    onValueChange = { newClassName = it },
                    label = { Text("Class Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = selectedTimeSlot,
                    onValueChange = {},
                    label = { Text("Time Slot") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { timeSlotDialogOpen = true },
                    readOnly = true
                )
                if (timeSlotDialogOpen) {
                    AlertDialog(
                        onDismissRequest = { timeSlotDialogOpen = false },
                        title = { Text("Select Time Slot") },
                        text = {
                            Column {
                                allSlots.forEach { slot ->
                                    TextButton(
                                        onClick = {
                                            selectedTimeSlot = slot
                                            timeSlotDialogOpen = false
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(slot)
                                    }
                                }
                            }
                        },
                        confirmButton = {},
                        dismissButton = {}
                    )
                }
                Button(
                    onClick = {
                        if (newClassName.isNotBlank() && selectedTimeSlot.isNotBlank()) {
                            if (myClasses.any { it.timeSlot == selectedTimeSlot }) {
                                errorMsg = "Slot already taken!"
                            } else {
                                ClassRepository.addClass(
                                    ClassInfo(
                                        newClassName,
                                        selectedTimeSlot,
                                        teacherEmail
                                    )
                                )
                                newClassName = ""
                                selectedTimeSlot = ""
                            }
                        }
                    },
                    modifier = Modifier.padding(top = 12.dp)
                ) { Text("Add Class") }
                errorMsg?.let {
                    LaunchedEffect(it) {
                        snackbarHostState.showSnackbar(it)
                        errorMsg = null
                    }
                }
                Spacer(Modifier.height(28.dp))
                Text("Your Classes", style = MaterialTheme.typography.headlineSmall)
            } else {
                Text("Your Schedule", style = MaterialTheme.typography.headlineSmall)
            }
            myClasses.forEach {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                    ) {
                        Text(
                            it.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            it.timeSlot,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        if (role != UserRole.Teacher) {
                            Text(
                                "By ${it.teacher}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}
