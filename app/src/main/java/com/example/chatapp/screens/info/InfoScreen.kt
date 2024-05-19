package com.example.chatapp.screens.info

import androidx.compose.runtime.Composable
import com.fatherofapps.jnav.annotations.JNav


@JNav(
    name = "InfoScreenNavigation",
    baseRoute = "info_route",
    destination = "info_destination",
)
@Composable
fun InfoScreen(
    viewModel: InfoViewModel,
    popBackStack:()-> Unit
) {
    
}