package com.fiap.on.fase_4.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fiap.on.fase_4.data.local.AppDatabase
import com.fiap.on.fase_4.data.repository.WineRepository
import com.fiap.on.fase_4.ui.screen.addedit.AddEditWineScreen
import com.fiap.on.fase_4.ui.screen.winelist.WineListScreen
import com.fiap.on.fase_4.ui.viewmodel.WineViewModel
import com.fiap.on.fase_4.ui.viewmodel.WineViewModelFactory

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val wineViewModel: WineViewModel = viewModel(
        factory = WineViewModelFactory(WineRepository(AppDatabase.getDatabase(context).wineDao()))
    )

    NavHost(navController = navController, startDestination = "wineList") {
        composable("wineList") {
            WineListScreen(navController = navController, viewModel = wineViewModel)
        }
        composable(
            route = "addEditWine?wineId={wineId}",
            arguments = listOf(navArgument("wineId") {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            val wineId = backStackEntry.arguments?.getString("wineId")?.toIntOrNull()
            AddEditWineScreen(
                navController = navController,
                viewModel = wineViewModel,
                wineId = wineId
            )
        }
    }
}