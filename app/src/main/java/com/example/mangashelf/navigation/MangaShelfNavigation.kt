package com.example.mangashelf.navigation

import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mangashelf.ui.screens.MangaDetailsScreen
import com.example.mangashelf.ui.screens.MangaListScreen
import com.example.mangashelf.R
import com.example.mangashelf.network.models.Manga
import com.google.gson.Gson

const val MANGA_JSON = "mangaJson"
const val MANGA_LIST_ROUTE = "manga_list"
const val MANGA_DETAILS_ROUTE = "manga_details/{$MANGA_JSON}"
const val MANGA_DETAILS_ROUTE_BASE = "manga_details"

@Composable
fun MangaShelfNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MANGA_LIST_ROUTE) {

        // Composable for Manga List Screen
        composable(
            route = MANGA_LIST_ROUTE,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(1000)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(1000)
                )
            }
        ) {
            MangaListScreen { mangaJson ->
                // Encode JSON to avoid special character issues
                val encodedJson = Uri.encode(mangaJson)
                navController.navigate(MANGA_DETAILS_ROUTE_BASE + "/${encodedJson}")
            }
        }

        // Composable for Manga Details Screen
        composable(
            route = MANGA_DETAILS_ROUTE,
            arguments = listOf(navArgument(MANGA_JSON) { type = NavType.StringType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(1000)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(1000)
                )
            }
        ) { navBackStackEntry ->
            val json = navBackStackEntry.arguments?.getString(MANGA_JSON)?.let { Uri.decode(it) }
            val manga = json?.let {
                try {
                    Gson().fromJson(it, Manga::class.java)
                } catch (e: Exception) {
                    null
                }
            }
            if (manga != null) {
                MangaDetailsScreen(manga = manga) {
                    navController.navigateUp()
                }
            } else {
                Toast.makeText(
                    LocalContext.current,
                    stringResource(id = R.string.error_while_navigating),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
