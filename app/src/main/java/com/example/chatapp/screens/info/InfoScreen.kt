package com.example.chatapp.screens.info

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.model.Account
import com.example.chatapp.screens.settings.SettingScreen
import com.example.chatapp.screens.signIn.AuthViewModel
import com.example.chatapp.ui.components.BottomNavigation
import com.example.chatapp.ui.components.RoundIconButton
import com.fatherofapps.jnav.annotations.JNav


@OptIn(ExperimentalMaterial3Api::class)
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
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onTertiary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),

                title = {
                },
                navigationIcon = {
                    RoundIconButton(
                        null,
                        imageVector = Icons.Default.ArrowBack,
                        modifier = Modifier
                            .fillMaxHeight(),
                        onClick = popBackStack,
                    )
                }
            )
        },
        ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TopProfile()
        }
    }
}

@Composable
fun BodyProfile(
    viewModel: InfoViewModel,
) {
    Column(
        Modifier
            .fillMaxSize(),

    ) {

    }
}
@Composable
fun TopProfile(
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(200.dp),
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
        Row {

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InfoPreview() {
    InfoScreen (
        InfoViewModel(),
        popBackStack = {},
    )
}
