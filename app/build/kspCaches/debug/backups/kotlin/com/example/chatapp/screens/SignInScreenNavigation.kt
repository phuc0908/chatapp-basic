package com.example.chatapp.screens

import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlin.String

public object SignInScreenNavigation {
  public val destination: String = "sign_in_destination"

  public val route: String = "sign_in_route"

  public fun createRoute(): String = "sign_in_route"
}
