package com.example.hello_there.screens.attendance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AttendanceScreen() {
    // Mock data: class -> list of students
    val classData = mapOf(
        "Android Dev 101" to listOf("Alice", "Bob", "Charlie"),
        "Data Structures" to listOf("Diana", "Edward", "Fiona"),
        "Cloud Computing" to listOf("George", "Helen")
    )

    // Track attendance by Pair<class, student>
    val attendanceStates = remember {
        mutableStateMapOf<Pair<String, String>, Boolean>().apply {
            classData.forEach { (clazz, students) ->
                students.forEach { this[clazz to it] = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Take Attendance",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        classData.forEach { (clazz, students) ->
            Text(
                text = clazz,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            students.forEach { name ->
                val key = clazz to name
                Card(
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = attendanceStates[key] == true,
                            onCheckedChange = { attendanceStates[key] = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                uncheckedThumbColor = Color.LightGray
                            )
                        )
                        Text(
                            if (attendanceStates[key] == true) "Present" else "Absent",
                            color = if (attendanceStates[key] == true) MaterialTheme.colorScheme.primary else Color.Red,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { /* TODO: handle submit action */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Submit Attendance")
        }
    }
}
