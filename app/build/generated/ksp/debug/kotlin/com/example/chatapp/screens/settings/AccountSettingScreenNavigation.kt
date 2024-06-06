package com.example.chatapp.screens.settings

import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlin.String

public object AccountSettingScreenNavigation {
  public val destination: String = "account_setting_destination"

  public val route: String = "account_setting_route"

  public fun createRoute(): String = "account_setting_route"
}
