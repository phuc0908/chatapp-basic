package com.example.chatapp.screens

import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlin.String

public object ContactScreenNavigation {
  public val destination: String = "contact_destination"

  public val route: String = "contact_route"

  public fun createRoute(): String = "contact_route"
}
