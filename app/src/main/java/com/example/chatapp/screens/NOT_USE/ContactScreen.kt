package com.example.chatapp.screens.NOT_USE

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.screens.Destination
import com.example.chatapp.screens.MediaScreenNavigation
import com.example.chatapp.viewmodel.ContactViewModel
import com.example.chatapp.ui.components.BottomNavigation
import com.fatherofapps.jnav.annotations.JNav
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                contentPadding = PaddingValues(0.dp)
            ) {
                BottomNavigation(2, navController)
            }
        },

        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                Button(
                    onClick = {
                        val mediaUrl = "0f77b62d-a4cc-4853-9019-7b30accbcdb0?alt=media&token=298d5e2a-f463-4275-aaca-c698a0c25878"
                        val encodedUrl = URLEncoder.encode(mediaUrl, StandardCharsets.UTF_8.toString())
                        Log.d("ENCODE",encodedUrl)
                        navController.navigate(
                            MediaScreenNavigation.createRoute(
                                mediaUrl = encodedUrl
                            )
                        )
                    }
                )
                {
                   Text(text = "Button")
                }
            }
        }
}