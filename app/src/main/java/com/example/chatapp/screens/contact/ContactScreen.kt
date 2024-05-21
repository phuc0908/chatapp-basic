package com.example.chatapp.screens.contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.screens.call.TopBar
import com.example.chatapp.ui.components.BottomNavigation
import com.example.chatapp.ui.components.RoundIconButton
import com.fatherofapps.jnav.annotations.JNav

@OptIn(ExperimentalMaterial3Api::class)
@JNav(
    name = "ContactScreenNavigation",
    baseRoute = "contact_route",
    destination = "contact_destination",
)
@Composable
fun ContactScreen(
    viewModel: ContactViewModel,
    popBackStack: () -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),

                title = {
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {

                BottomNavigation(2,navController)
            }
        },

        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {

            }
        }
}