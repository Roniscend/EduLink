package com.example.hello_there.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hello_there.screens.common.BottomNavBar
import com.example.hello_there.screens.home.HomeScreen
import com.example.hello_there.screens.classes.ClassesScreen
import com.example.hello_there.screens.attendance.AttendanceScreen
import com.example.hello_there.screens.profile.ProfileScreen
import com.example.hello_there.screens.login.LoginScreen
import com.example.hello_there.screens.teacher.TeacherDashboard
import com.example.hello_there.screens.student.StudentDashboard
import com.example.hello_there.screens.teacher.TeacherRoot
import com.example.hello_there.screens.student.StudentRoot

object Routes {
    const val LOGIN = "login"
    const val TEACHER_ROOT = "teacher_root"
    const val TEACHER_ROOT_ARG = "teacher_root/{email}"
    const val STUDENT_ROOT = "student_root"
    const val HOME = "home"
    const val CLASSES = "classes"
    const val ATTENDANCE = "attendance"
    const val PROFILE = "profile"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(
            route = Routes.TEACHER_ROOT_ARG,
            arguments = listOf(androidx.navigation.navArgument("email") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val teacherEmail = backStackEntry.arguments?.getString("email") ?: ""
            TeacherRoot(teacherEmail)
        }
        composable(Routes.STUDENT_ROOT) {
            StudentRoot()
        }
    }
}
