package com.example.chatapp.screens

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlin.String
import kotlin.collections.List

public object SearchScreenNavigation {
  public const val userNameArg: String = "userNameArg"

  public val destination: String = "search_destination"

  public val route: String = "search_route/$userNameArg={$userNameArg}"

  public fun arguments(): List<NamedNavArgument> {
                    // list of arguments
                        return listOf(navArgument(userNameArg){
         type = NavType.StringType
         nullable = false
        })
  }

  public fun userName(navBackStackEntry: NavBackStackEntry): String {
        // 
        return navBackStackEntry.arguments?.getString(userNameArg) ?: throw
            IllegalArgumentException("userName is required")
  }

  public fun createRoute(userName: String): String = "search_route/$userNameArg=$userName"
}
