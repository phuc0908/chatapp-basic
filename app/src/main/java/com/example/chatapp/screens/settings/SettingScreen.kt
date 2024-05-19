package com.example.chatapp.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.Destination
import com.example.chatapp.ui.components.BottomNavigation
import com.example.chatapp.ui.components.RoundIconButton
import com.fatherofapps.jnav.annotations.JNav
import com.example.chatapp.R
import com.example.chatapp.screens.signIn.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)

@JNav(
    name = "SettingScreenNavigation",
    baseRoute = "setting_route",
    destination = "setting_destination",
)

@Composable
fun SettingScreen(
    popBackStack: () -> Unit,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    Scaffold(
        topBar =
        {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(0.dp)
                ,
                title = {
                    TopBar()
                },
                navigationIcon = {
                    RoundIconButton(
                        null,
                        imageVector = Icons.Default.ArrowBack,
                        modifier = Modifier
                            .fillMaxHeight(),
                        onClick = popBackStack
                        ,
                    )
                },
                actions = {
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                BottomNavigation(3, navController)
            }
        },
    ) {innerPadding->
        Column(
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ){
            Profile(authViewModel,navController)
            BodySetting(navController)
        }
    }
}

@Composable
fun Profile(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(220.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundIconButton(
            imageResId = R.drawable.newuser,
            imageVector = null,
            modifier = Modifier.size(120.dp)
        ) {}
        Text(text = "Nguyen Hong Phuc",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "phucnh.22itb@vku.udn.vn",
            fontSize = 10.sp,
            fontWeight = FontWeight.Light,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                authViewModel.signOut(navController = navController)
            },
            Modifier.height(35.dp)
        ) {
            Text(text = "Sign out")
        }
    }
}

@Composable
fun BodySetting(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        RowInSetting(
            R.drawable.lock,
            "Acount",
            "Privacy, change my infomation",
            onClick = {

            }
        )
        RowInSetting(
            R.drawable.dark_theme,
            "Dark theme",
            "System",
            onClick = {
                navController.navigate(Destination.DarkTheme.route)
            }
        )
        RowInSetting(
            R.drawable.status_active,
            "Active status",
            "On",
            onClick = {

            }
        )
    }
}

const val heightRow = 56

@Composable
fun RowInSetting(
    imageResId: Int,
    nameRow: String,
    status: String? = null,
    onClick: () -> Unit
    ) {
    Row (
        Modifier
            .fillMaxWidth()
            .height(heightRow.dp)
            .clickable(onClick = onClick)
            .padding(start = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        RoundIconButton(
            imageResId = imageResId,
            imageVector = null,
            modifier = Modifier.size(55.dp)) {}
        Spacer(modifier = Modifier.size(5.dp))
        Column {
            Text(
                text = nameRow,
                style = TextStyle(
                    fontSize = 15.sp
                )
            )
            if (status != null) {
                Text(text = status,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }

        }
    }
}

@Composable
fun TopBar() {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            style = TextStyle(
                fontSize = 25.sp
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingPreview() {
    SettingScreen (
        popBackStack = {},
        rememberNavController(),
        authViewModel = AuthViewModel(LocalContext.current)
    )
}
