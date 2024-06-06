package com.example.chatapp.screens

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlin.String
import kotlin.collections.List

public object MessageScreenNavigation {
  public const val uidArg: String = "uidArg"

  public val destination: String = "message_destination"

  public val route: String = "message_route/$uidArg={$uidArg}"

  public fun arguments(): List<NamedNavArgument> {
                    // list of arguments
                        return listOf(navArgument(uidArg){
         type = NavType.StringType
         nullable = false
        })
  }

  public fun uid(navBackStackEntry: NavBackStackEntry): String {
        // 
        return navBackStackEntry.arguments?.getString(uidArg) ?: throw
            IllegalArgumentException("uid is required")
  }

  public fun createRoute(uid: String): String = "message_route/$uidArg=$uid"
}
