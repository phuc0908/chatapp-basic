package com.example.chatapp.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.ui.components.BottomNavigation
import com.example.chatapp.model.Account
import com.example.chatapp.model.ChatItem
import com.example.chatapp.viewmodel.HomeViewModel
import com.example.chatapp.screens.settings.AvatarIcon
import com.example.chatapp.ui.components.RoundIconButton
import com.example.chatapp.ui.components.TextChat
import com.example.chatapp.ui.components.TextNameUser
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
    currentAccount: Account?,
    navController: NavController,
    openSearch:() -> Unit,
    openMyInfo:() -> Unit,
    openChat:(String) -> Unit,
    ) {
    var backPressedOnce by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val chatItems by viewModel.chatItemList




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
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),

                title = {
                    Row (
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text("Home", color = Green1)
                    }

                },
                navigationIcon = {
                    Row (
                        Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        RoundIconButton(
                            null,
                            imageVector = Icons.Default.Search,
                            modifier = Modifier
                                .size(50.dp)
                                .aspectRatio(1f),
                            onClick = openSearch,
                        )
                    }

                },
                actions = {
                    Row (
                        Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        if (currentAccount != null) {
                            AvatarIcon(
                                imageUrl = currentAccount.imageUri,
                                modifier = Modifier
                                    .size(50.dp)
                                    .aspectRatio(1f),
                                onClick = openMyInfo
                            )
                        }else{
                            RoundIconButton(imageResId = R.drawable.newuser,
                                imageVector = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .aspectRatio(1f),
                                onClick = openMyInfo
                            )
                        }
                    }

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

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {

                ListOfStatusFriend(chatItems)
                ListMyChat(openChat, chatItems)
        }
    }
}



@Composable
fun OneChatFriend(
    openChat: (String) -> Unit,
    uid: String,
    avatar: String = "",
    name: String,
    lastMessage: String,
    lastTimeMessage: Long?,
) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .clickable(
            onClick = {
                openChat(uid)
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
            AvatarIcon(
                imageUrl = avatar,
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
    text: Long?,
) {
    Text(text = text?.let { parseTimestampToString(it) }.toString(),
        style = TextStyle(fontSize = 8.sp,
            fontWeight = FontWeight.W300
        ),
        maxLines = 1)
}

@Composable
fun ListMyChat(
    openChat:(String)-> Unit,
    friends: List<ChatItem>,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ){
        friends.forEach {friend->
            OneChatFriend(
                openChat = {
                    openChat(friend.id)
               },
                uid = friend.id,
                avatar = friend.avatar,
                name = friend.name,
                lastMessage = friend.lastMessage,
                lastTimeMessage = friend.timestamp,
            )
        }
    }
}

fun parseTimestampToString(timestamp:Long?):String{
    timestamp ?: return "Không có tin nhắn"

    val currentTime = System.currentTimeMillis()
    val diffInMillis = currentTime - timestamp
    val diffInMinutes = diffInMillis / 60000

    return when {
        diffInMinutes < 1 -> "Vừa xong"
        diffInMinutes == 1L -> "1 phút trước"
        diffInMinutes < 60 -> "$diffInMinutes phút trước"
        diffInMinutes < 1440 -> "${diffInMinutes / 60} giờ trước"
        else -> "${diffInMinutes / 1440} ngày trước"
    }
}

@Composable
fun StatusFriend(
    friends: List<ChatItem>
) {
    friends.forEach {friend->
        Column (modifier = Modifier
            .height(100.dp)
            .width(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            AvatarIcon(
                imageUrl = friend.avatar,
                modifier = Modifier
                    .width(65.dp)
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
    friends: List<ChatItem>
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
        Account(),
        rememberNavController(),
        openSearch = {},
        openMyInfo = {},
        {}
    )
}

