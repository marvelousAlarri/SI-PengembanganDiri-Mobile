package com.example.sipengembangandiridtedi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sipengembangandiridtedi.ui.main.MainShell
import com.example.sipengembangandiridtedi.ui.screens.landing.LandingScreen
import com.example.sipengembangandiridtedi.ui.screens.login.LoginScreen
import com.example.sipengembangandiridtedi.ui.screens.otp.OtpScreen
import com.example.sipengembangandiridtedi.ui.screens.register.RegisterScreen
import com.example.sipengembangandiridtedi.ui.screens.resetpassword.ResetPasswordScreen

sealed class Route(val path: String) {
    data object Landing : Route("landing")
    data object Login : Route("login")
    data object Register : Route("register")
    data object ResetPassword : Route("reset_password")
    data object Main : Route("main")

    // from = "register" atau "forgot"
    data object Otp : Route("otp/{from}") {
        fun create(from: String) = "otp/$from"
    }
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Route.Landing.path) {

        composable(Route.Landing.path) {
            LandingScreen(
                onLogin = { navController.navigate(Route.Login.path) },
            )
        }

        composable(Route.Login.path) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onRegister = { navController.navigate(Route.Register.path) },
                onForgot = { navController.navigate(Route.Otp.create("forgot")) },
                onSuccess = {
                    navController.navigate(Route.Main.path) {
                        // hapus Landing + Login dari back stack, supaya Back tidak kembali ke login
                        popUpTo(Route.Landing.path) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }

        composable(Route.Register.path) {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onLogin = { navController.popBackStack() },
                onRegistered = { navController.navigate(Route.Otp.create("register")) },
            )
        }

        composable(Route.Otp.path) { entry ->
            val from = entry.arguments?.getString("from")

            OtpScreen(
                onBack = { navController.popBackStack() },
                onConfirmed = {
                    if (from == "forgot") {
                        navController.navigate(Route.ResetPassword.path)
                    } else {
                        navController.navigate(Route.Login.path) {
                            popUpTo(Route.Landing.path)
                        }
                    }
                },
            )
        }

        composable(Route.Main.path) {
            MainShell(
                onLogout = {
                    // kembali ke Login, dan kosongkan semua riwayat layar sebelumnya
                    navController.navigate(Route.Login.path) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.ResetPassword.path) {
            ResetPasswordScreen(
                onBack = { navController.popBackStack() },
                onDone = {
                    navController.navigate(Route.Login.path) {
                        popUpTo(Route.Landing.path)
                    }
                },
            )
        }
    }
}