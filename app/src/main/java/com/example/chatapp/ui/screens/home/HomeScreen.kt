package com.example.chatapp.ui.screens.home

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import com.example.chatapp.ui.theme.Green1
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ui.components.BottomNavigation
import com.example.chatapp.R
import com.example.chatapp.model.User
import com.example.chatapp.ui.components.RoundIconButton
import com.example.chatapp.ui.components.TextChat
import com.example.chatapp.ui.components.TextNameUser
import com.example.chatapp.ui.screens.signIn.AuthViewModel
import com.fatherofapps.jnav.annotations.JNav
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@JNav(
    name = "HomeScreenNavigation",
    baseRoute = "home_route",
    destination = "home_destination",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    navController: NavController,
    openFriendChat:() -> Unit,
    openSearch:() -> Unit,
    openMyinfo:() -> Unit,
    ) {
    var backPressedOnce by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        if (backPressedOnce) {
            (navController.context as? ComponentActivity)?.finish()
        } else {
            backPressedOnce = true
            Toast.makeText(navController.context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            coroutineScope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),

                title = {
                    TopBar(
                        openSearch = openSearch,
                        openMyinfo = openMyinfo
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                BottomNavigation(0, navController)
            }
        },
    ) { innerPadding ->
        LaunchedEffect(true) {
            viewModel.fetchStatusFriend()
            viewModel.fetchLastFriend()
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {

            viewModel.statusFriend?.let {
                ListOfStatusFriend(it)
            }
            viewModel.lastFriend?.let {
                ListMyChat(openFriendChat = openFriendChat,it)
            }
        }
    }
}


@Composable
fun TopBar(
    openSearch: () -> Unit,
    openMyinfo: () -> Unit,
    ) {
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        RoundIconButton(
            null,
            imageVector = Icons.Default.Search,
            modifier = Modifier
                .size(50.dp)
                .aspectRatio(1f),
            onClick = openSearch,
        )
        Text("Home", color = Green1)

        RoundIconButton(
            imageResId = R.drawable.avatar_garena_2,
            null,
            modifier = Modifier.size(50.dp,50.dp),
            onClick = openMyinfo
        )
    }
}



@Composable
fun OneChatFriend(
    avatar: Int?,
    name: String,
    lastMessage: String,
    lastTimeMessage: String,
    openFriendChat: () -> Unit
) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .clickable(
            onClick = {
                openFriendChat()
            }
        )
        .padding(start = 15.dp, end = 15.dp)
        .height(75.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
//        Avatar
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .width(70.dp),
                verticalArrangement = Arrangement.Center
        ){
            RoundIconButton(
                imageResId = avatar,
                null,
                modifier = Modifier
                    .width(65.dp)
                    .aspectRatio(1f)
            ) {}
        }
//        Other
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                TextNameUser(name)
                TimeAgoChat(lastTimeMessage)
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TextChat(lastMessage)
            }
        }
    }
}

@Composable
fun TimeAgoChat(
    text: String
) {
    Text(text = "$text ago",
        style = TextStyle(fontSize = 8.sp,
            fontWeight = FontWeight.W300
        ),
        maxLines = 1)
}

@Composable
fun ListMyChat(
    openFriendChat: () -> Unit,
    friends: List<User>
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ){
        friends.forEach {friend->
            friend.lastMessage?.let {
                OneChatFriend(
                    avatar = friend.avatar,
                    name = friend.name,
                    lastMessage = it,
                    lastTimeMessage = friend.timeAgo.toString(),
                    openFriendChat = openFriendChat
                )
            }
        }
    }
}

@Composable
fun StatusFriend(
    friends: List<User>
) {
    friends.forEach {friend->
        Column (modifier = Modifier
            .height(100.dp)
            .width(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            RoundIconButton(
                imageResId = friend.avatar,
                null,
                modifier = Modifier
                    .width(70.dp)
                    .aspectRatio(1f)
            ) {}
            Text(text = friend.name,
                modifier = Modifier.padding(top = 0.dp),
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun ListOfStatusFriend(
    friends: List<User>
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
    ) {
        StatusFriend(friends = friends)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    HomeScreen (
        viewModel = viewModel(),
        authViewModel = AuthViewModel(LocalContext.current),
        rememberNavController(),
        openFriendChat = {},
        openSearch = {},
        openMyinfo = {},
    )
}

