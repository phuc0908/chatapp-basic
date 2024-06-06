package com.example.chatapp.screens

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.chatapp.ui.components.AvatarIcon
import com.example.chatapp.ui.components.RoundIconButton
import com.example.chatapp.viewmodel.AccountViewModel
import com.example.chatapp.viewmodel.InfoViewModel
import com.example.chatapp.viewmodel.MessageViewModel
import com.fatherofapps.jnav.annotations.JNav
import com.fatherofapps.jnav.annotations.JNavArg
import com.google.firebase.database.FirebaseDatabase


@OptIn(ExperimentalMaterial3Api::class)
@JNav(
    name = "InfoScreenNavigation",
    baseRoute = "info",
    destination = "info_destination",
    arguments = [
        JNavArg(name = "uid", type = String::class)
    ])
@Composable
fun InfoScreen(
    viewModel: InfoViewModel,
    popBackStack:()-> Unit,
    navController: NavController,
    uid: String
) {
    LaunchedEffect(Unit) {
        Log.d("InfoScreen", "LaunchedEffect triggered")
        viewModel.getFriend(uid)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary,
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
                .background(MaterialTheme.colorScheme.onSurface)
        ) {
            viewModel.user.value?.let { TopProfile(it,navController) }
            viewModel.user.value?.let { BodyProfile(it) }
        }

    }
}

@Composable
fun BodyProfile(
    account : Account
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(600.dp)
            .clip(
                RoundedCornerShape(
                    topStart = CornerSize(30.dp),
                    topEnd = CornerSize(30.dp),
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            .background(MaterialTheme.colorScheme.onTertiary)
        ,
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        RowInfo("Display Name",account.nickName)
        RowInfo("Email Address",account.username)
        RowInfo("Phone Number","...")
    }
}

@Composable
fun RowInfo(
    title: String,
    info: String
) {
    Row (
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 25.dp, top = 15.dp)
    ){
        Column {
            Text(text = title,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal
            )
            Text(text = info,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
@Composable
fun TopProfile(
    account: Account,
    navController: NavController,
) {
    Column (
        Modifier.background(MaterialTheme.colorScheme.onSurface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            Modifier
                .fillMaxWidth()
                .height(180.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AvatarIcon(
                imageUrl = account.imageUri,
                modifier = Modifier.size(120.dp),
                isOnline = false
            ) {}
            Text(text = account.nickName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary
            )
            Text(text = account.username,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onTertiary
            )
            Spacer(modifier = Modifier.height(10.dp))

        }
        Row (
            Modifier
                .width(200.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            RoundIconButton(imageResId = R.drawable.chat_bubble,
                imageVector = null ,
                colorTint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray)
                    .clickable {
                        navController.navigate(MessageScreenNavigation.createRoute(account.uid))
                    }
            ) {

            }
            RoundIconButton(imageResId = R.drawable.call,
                imageVector = null ,
                colorTint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray)
            ) {
            }
            RoundIconButton(imageResId = R.drawable.square_outline,
                imageVector = null ,
                colorTint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray)
            ) {

            }
        }
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InfoPreview() {
    InfoScreen (
            InfoViewModel(),
        popBackStack = {},
        rememberNavController(),
        ""
    )
}
