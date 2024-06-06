package com.example.chatapp.screens

import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlin.String

public object HomeScreenNavigation {
  public val destination: String = "home_destination"

  public val route: String = "home_route"

  public fun createRoute(): String = "home_route"
}
