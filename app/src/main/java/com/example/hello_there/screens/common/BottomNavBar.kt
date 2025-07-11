package com.example.hello_there.screens.common

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.hello_there.navigation.Routes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.List

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    object Home : BottomNavItem(Routes.HOME, "Home", Icons.Default.Home)
    object Classes : BottomNavItem(Routes.CLASSES, "Classes", Icons.Default.List)
    object Attendance : BottomNavItem(Routes.ATTENDANCE, "Attendance", Icons.Default.CheckCircle)
    object Profile : BottomNavItem(Routes.PROFILE, "Profile", Icons.Default.Person)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Classes,
    BottomNavItem.Attendance,
    BottomNavItem.Profile
)

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onTabSelected: (String) -> Unit,
    showAttendance: Boolean = true
) {
    val items =
        if (showAttendance) bottomNavItems else bottomNavItems.filter { it !is BottomNavItem.Attendance }

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEach { item ->
            NavigationBarItem(
                selected = (currentRoute == item.route),
                onClick = { onTabSelected(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
