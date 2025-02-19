package com.example.chatapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.R
import com.example.chatapp.viewmodel.AuthViewModel
import com.fatherofapps.jnav.annotations.JNav
import kotlinx.coroutines.delay


@JNav(
    name = "SplashScreenNavigation",
    baseRoute = "splash_route",
    destination = "splash_destination",
)
@Composable
fun SplashScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    LaunchedEffect(Unit) {
        delay(1000)
        if(authViewModel.currentUser!=null){
            navController.navigate(HomeScreenNavigation.route)
        }else{
            navController.navigate(Destination.SignIn.route)
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            Modifier.size(100.dp)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SplashPreview() {
//    SplashScreen()
}