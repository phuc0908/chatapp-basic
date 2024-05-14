package com.example.chatapp.ui.screens.contact

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.fatherofapps.jnav.annotations.JNav

@JNav(
    name = "ContactScreenNavigation",
    baseRoute = "contact_route",
    destination = "contact_destination",
)
@Composable
fun ContactSreen(
    viewModel: ContactViewModel,
    popBackStack: () -> Unit,
    navController: NavController
) {

}