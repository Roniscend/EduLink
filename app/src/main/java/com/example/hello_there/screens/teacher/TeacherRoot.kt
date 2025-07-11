package com.example.hello_there.screens.teacher

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.example.hello_there.navigation.Routes
import com.example.hello_there.screens.attendance.AttendanceScreen
import com.example.hello_there.screens.classes.ClassesScreen
import com.example.hello_there.screens.classes.UserRole
import com.example.hello_there.screens.common.BottomNavBar
import com.example.hello_there.screens.home.HomeScreen
import com.example.hello_there.screens.profile.TeacherProfileScreen
import androidx.compose.material3.Scaffold

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TeacherRoot(teacherEmail: String) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onTabSelected = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() },
        ) {
            composable(Routes.HOME) { HomeScreen() }
            composable(Routes.CLASSES) {
                ClassesScreen(
                    role = UserRole.Teacher,
                    teacherEmail = teacherEmail
                )
            }
            composable(Routes.ATTENDANCE) { AttendanceScreen() }
            composable(Routes.PROFILE) { TeacherProfileScreen() }
        }
    }
}
