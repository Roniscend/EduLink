package com.example.hello_there.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hello_there.data.CredentialRepository
import com.example.hello_there.navigation.Routes
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val repo = remember { CredentialRepository(context) }
    val scope = rememberCoroutineScope()

    var role by remember { mutableStateOf("teacher") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    // Ensure defaults
    LaunchedEffect(Unit) { repo.ensureDefaultAccounts() }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Teacher")
                Switch(checked = role == "student", onCheckedChange = {
                    role = if (it) "student" else "teacher"
                })
                Text("Student")
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    val emailTrimmed = email.trim()
                    val ok = repo.validate(role, emailTrimmed, password)
                    if (ok) {
                        if (role == "teacher") {
                            navController.navigate("teacher_root/$emailTrimmed") {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Routes.STUDENT_ROOT) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        }
                    } else {
                        error = "Invalid credentials"
                    }
                }
            }) {
                Text("Login")
            }
        }
    }
}
