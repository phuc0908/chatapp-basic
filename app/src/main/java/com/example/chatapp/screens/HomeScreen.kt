package com.example.chatapp.screens

import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import com.example.chatapp.ui.theme.Green1
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
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
import com.example.chatapp.model.Account
import com.example.chatapp.model.ChatItem
import com.example.chatapp.screens.showDialog.ConfirmDeleteDialog
import com.example.chatapp.screens.showDialog.ConfirmDeleteHomeDialog
import com.example.chatapp.screens.showDialog.OptionsHomeDialog
import com.example.chatapp.ui.components.AvatarIcon
import com.example.chatapp.viewmodel.HomeViewModel
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
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

    var showOptionsHomeDialog by remember { mutableStateOf(false) }
    var showConfirmDeleteDialog by remember { mutableStateOf(false) }
    var idFriend by remember { mutableStateOf("") }

    val context = LocalContext.current


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
                                onClick = openMyInfo,
                                isOnline = false
                            )
                        }else{
                            AvatarIcon(
                                imageUrl = "https://firebasestorage.googleapis.com/v0/b/chatapp-4e975.appspot.com/o/avatars%2FnewUser.png?alt=media&token=428ada2b-c505-4af7-9da7-370ea0086e56",
                                modifier = Modifier
                                    .size(50.dp)
                                    .aspectRatio(1f),
                                onClick = openMyInfo,
                                isOnline = false
                            )
                        }
                    }

                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                contentPadding = PaddingValues(0.dp)
            ) {
                BottomNavigation(0, navController)
            }
        },
    ) { innerPadding ->
        LaunchedEffect(Unit) {
            if (currentAccount != null) {
                viewModel.fetchAccountListWithLastMessages(currentAccount.uid)
            }
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            ListOfStatusFriend(openChat, chatItems)
// ====================== LIST CHAT ITEMS ==========================================
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
            ){
                chatItems.forEach {friend->
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {
                                openChat(friend.id)
                            },
                            onLongClick = {
                                idFriend = friend.id
                                showOptionsHomeDialog = true
                            },
                        )
                        .padding(start = 15.dp, end = 15.dp)
                        .height(75.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                    //      Avatar
                        AvatarIcon(
                            imageUrl = friend.avatar,
                            modifier = Modifier
                                .width(65.dp)
                                .aspectRatio(1f),
                            isOnline = friend.isOnline == true && friend.activeStatus == "ON"
                        ) {}

                    //      Other
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
                                TextNameUser(friend.name)
                                TimeAgoChat(friend.timestamp)
                            }
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 50.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                TextChat(friend.lastMessage)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showOptionsHomeDialog) {
        OptionsHomeDialog(
            showDialog = true,
            onSave = {
                showOptionsHomeDialog = false
            },
            onDelete = {
                showOptionsHomeDialog = false
                showConfirmDeleteDialog = true
            },
            onDismiss = {
                showOptionsHomeDialog = false
            },
        )
    }
    if (showConfirmDeleteDialog) {
        ConfirmDeleteHomeDialog(
            showDialog = true,
            onConfirm = {
                if (currentAccount != null) {
                        viewModel.deleteAllMessagesWithAFriend(context, currentAccount.uid, idFriend)
                }
                showConfirmDeleteDialog = false
            },
            onDismiss = {
                showConfirmDeleteDialog = false
            }
        )
    }
}



@Composable
fun TimeAgoChat(
    text: Long?,
) {
    Text(text =
    text?.let { parseTimestampToString(it) }?.toString() ?: "",
        style = TextStyle(fontSize = 8.sp,
            fontWeight = FontWeight.W300
        ),
        maxLines = 1)
}

fun parseTimestampToString(timestamp:Long?):String{
    timestamp ?: return "No messages"

    val currentTime = System.currentTimeMillis()
    val diffInMillis = currentTime - timestamp
    val diffInMinutes = diffInMillis / 60000

    return when {
        diffInMinutes < 1 -> "Just now"
        diffInMinutes == 1L -> "1 minute ago"
        diffInMinutes < 60 -> "$diffInMinutes minutes ago"
        diffInMinutes < 1440 -> "${diffInMinutes / 60} hours ago"
        else -> "${diffInMinutes / 1440} days ago"
    }
}

@Composable
fun StatusFriend(
    friends: List<ChatItem>,
    openChat:(String)-> Unit,
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
                    .aspectRatio(1f),
                isOnline =  friend.activeStatus!="OFF" && friend.isOnline == true
            ) {
                openChat(friend.id)
            }
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
    openChat:(String)-> Unit,
    friends: List<ChatItem>
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
    ) {
        StatusFriend(friends = friends, openChat)
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

