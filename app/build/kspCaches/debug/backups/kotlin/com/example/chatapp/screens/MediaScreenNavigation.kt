package com.example.chatapp.screens

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlin.String
import kotlin.collections.List

public object MediaScreenNavigation {
  public const val mediaUrlArg: String = "mediaUrlArg"

  public val destination: String = "media_mes_destination"

  public val route: String = "media_mes/$mediaUrlArg={$mediaUrlArg}"

  public fun arguments(): List<NamedNavArgument> {
                    // list of arguments
                        return listOf(navArgument(mediaUrlArg){
         type = NavType.StringType
         nullable = false
        })
  }

  public fun mediaUrl(navBackStackEntry: NavBackStackEntry): String {
        // 
        return navBackStackEntry.arguments?.getString(mediaUrlArg) ?: throw
            IllegalArgumentException("mediaUrl is required")
  }

  public fun createRoute(mediaUrl: String): String = "media_mes/$mediaUrlArg=$mediaUrl"
}
