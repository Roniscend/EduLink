package com.example.hello_there.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TeacherProfileScreen() {
    ProfileContent(name = "Jordan Smith", role = "Teacher")
}

@Composable
private fun ProfileContent(name: String, role: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .size(94.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                tint = Color.White,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(54.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(Modifier.height(18.dp))
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(name, style = MaterialTheme.typography.titleLarge)
                Text(role, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(12.dp))
                Text("email@example.com", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
