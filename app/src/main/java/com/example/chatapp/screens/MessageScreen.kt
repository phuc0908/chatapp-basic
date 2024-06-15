package com.example.chatapp.screens

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.Downloader
import com.example.chatapp.R
import com.example.chatapp.model.Account
import com.example.chatapp.ui.components.AvatarIcon
import com.example.chatapp.viewmodel.MessageViewModel
import com.example.chatapp.ui.components.ImageMessage
import com.example.chatapp.ui.components.Message
import com.example.chatapp.ui.components.RoundIconButton
import com.example.chatapp.ui.components.CustomTextField
import com.example.chatapp.ui.components.NotRoundIconButton
import com.example.chatapp.ui.components.TextNameUser
import com.example.chatapp.ui.components.VideoMessage
import com.fatherofapps.jnav.annotations.JNav
import com.fatherofapps.jnav.annotations.JNavArg
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class)

@JNav(
    name = "MessageScreenNavigation",
    baseRoute = "message_route",
    destination = "message_destination",
    arguments = [
        JNavArg(name = "uid", type = String::class)
    ]
)

@Composable
fun MessageScreen(
    viewModel: MessageViewModel,
    navController: NavController,
    downloader: Downloader,
    curentid: String,
    friendid: String,
    context: Context
) {
    val typeText = 0
    val typeImage = 1
    val typeVideo = 2
    val text  = remember{ mutableStateOf("") }
    val messages by viewModel.messageList


    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
        }
    }


    val listState = rememberLazyListState()
    LaunchedEffect(messages,Unit) {
        listState.animateScrollToItem(messages.size)
    }

    LaunchedEffect(Unit) {
        viewModel.getFriend(friendid)
    }
    var selectedMessageId by remember { mutableStateOf<String?>(null) }
    var selectedUserIdByMes by remember { mutableStateOf<String?>(null) }

    var selectedMessageText by remember { mutableStateOf<String?>(null) }
    var showOptionsMesDialog by remember { mutableStateOf(false) }
    var showOptionsImageDialog by remember { mutableStateOf(false) }
    var showOptionsVideoDialog by remember { mutableStateOf(false) }
    var showConfirmDeleteDialog by remember { mutableStateOf(false) }

    var heightBottomBar by remember { mutableStateOf(60.dp) }
    var heightTf by remember { mutableStateOf(30.dp) }
    var newlineCount by remember { mutableIntStateOf(1) }

    val focusManager = LocalFocusManager.current


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onTertiary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(0.dp)
                ,
                title = {
                    viewModel.user.value?.let {
                        TopBarMes(navController,it)
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.onTertiary,
                contentColor = MaterialTheme.colorScheme.onSurface,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(heightBottomBar)
                    .border(
                        border = BorderStroke(
                            width = 0.1.dp, color = Color.LightGray
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    verticalAlignment = Alignment.Bottom
                ){
                    NotRoundIconButton(
                        imageResId = R.drawable.media,
                        imageVector = null,
                        modifier = Modifier.size(45.dp)
                    ) {
                        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                            type = "*/*"
                            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
                        }
                        mediaPickerLauncher.launch(intent)
                    }

                    Column {
                        CustomTextField(
                            value = text,
                            modifier = Modifier
                                .padding(top = 1.dp, bottom = 1.dp, start = 5.dp, end = 5.dp)
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(percent = 45)
                                )
                                .height(heightTf)
                                .width(
                                    if (text.value.trim() != "")
                                        305.dp
                                    else
                                        345.dp
                                )
                                ,
                            fontSize = 14.sp,
                            placeholderText = "Send message...",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Default
                            ),
                            keyboardActions = KeyboardActions {
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                            maxLine = Int.MAX_VALUE,
                            onValueChange = {newText ->
                                val lines = newText.split("\n")
                                    .filter { line -> line.isNotBlank() }

                                newlineCount = lines.size
                                if (newlineCount>0) {
                                    heightTf = 32.dp + (25.dp * (newlineCount-1))
                                    heightBottomBar = 60.dp + (25.dp * (newlineCount-1))
                                }

                            }
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                    }
                    if(text.value.trim()!=""){
                        RoundIconButton(
                            imageResId = R.drawable.sendd,
                            imageVector = null,
                            modifier = Modifier.size(50.dp)
                        ) {
                            viewModel.sendMessage(text.value.trim(),curentid, friendid, typeText)
                            text.value = ""
                            heightTf = 32.dp
                            heightBottomBar = 60.dp
                        }
                    }else{
                        Spacer( Modifier.size(50.dp))
                    }
                    selectedImageUri?.let { uri ->

                        val contentResolver: ContentResolver = context.contentResolver
                        val mimeType = contentResolver.getType(uri) ?: ""
                        val type = when {
                            mimeType.startsWith("image") -> typeImage
                            mimeType.startsWith("video") -> typeVideo
                            else -> -1
                        }

                        viewModel.uploadImageMessage(context,uri){imageUrl->
                            viewModel.sendImageMessage(imageUrl,curentid,friendid,type)
                            selectedImageUri = null
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            items(messages) { mes ->
                when (mes.type) {
                    typeText ->
                        Message(
                            message = mes.message,
                            isMyMessage = mes.idFrom == curentid,
                            onLongPress = {
                                selectedMessageId =
                                if(mes.idFrom == curentid) {
                                    mes.id
                                }else{
                                    null
                                }
                                selectedMessageText = mes.message
                                selectedUserIdByMes = mes.idFrom
                                showOptionsMesDialog = true
                            }
                        )
                    typeImage ->
                        ImageMessage(
                            imageUrl = mes.message,
                            isMyImage = mes.idFrom == curentid,
                            onLongPress = {
                                selectedMessageId =
                                    if(mes.idFrom == curentid) {
                                        mes.id
                                    }else{
                                        null
                                    }
                                selectedMessageText = mes.message
                                selectedUserIdByMes = mes.idFrom
                                showOptionsImageDialog = true
                            }
                        )
                    typeVideo ->
                        VideoMessage(
                            mediaUrl = mes.message,
                            isMyVideo = mes.idFrom == curentid,
                            openMedia = {
                              navController.navigate(MediaScreenNavigation.createRoute(mes.message))
                            },
                            onLongPress = {
                                selectedMessageId =
                                    if(mes.idFrom == curentid) {
                                        mes.id
                                    }else{
                                        null
                                    }
                                selectedMessageText = mes.message
                                selectedUserIdByMes = mes.idFrom
                                showOptionsVideoDialog = true
                            }
                        )

                }
            }
        }

        val clipboardManager = LocalClipboardManager.current

        if (showOptionsMesDialog) {
            OptionsMesDialog(
                isMyMessage = selectedUserIdByMes == curentid,
                showDialog = true,
                onCopy = {
                    selectedMessageText?.let { text ->
                        clipboardManager.setText(AnnotatedString(text))
                    }
                    Toast.makeText(
                        context,
                        "Copied.",
                        Toast.LENGTH_SHORT
                    ).show()
                    showOptionsMesDialog = false
                },
                onDelete = {
                    showOptionsMesDialog = false
                    showConfirmDeleteDialog = true
                },
                onDismiss = {
                    showOptionsMesDialog = false
                }
            )
        }
        if (showOptionsImageDialog) {
            OptionsImageDialog(
                isMyImage = selectedUserIdByMes == curentid,
                showDialog = true,
                onDownload = {
                    showOptionsImageDialog = false
                    downloader.downloadFile(selectedMessageText.toString())
                    Toast.makeText(
                        context,
                        "Downloading picture.",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onDelete = {
                    showOptionsImageDialog = false
                    showConfirmDeleteDialog = true
                },
                onDismiss = {
                    showOptionsImageDialog = false
                }
            )
        }
        if (showOptionsVideoDialog) {
            OptionsVideoDialog(
                isMyVideo = selectedUserIdByMes == curentid,
                showDialog = true,
                onDownload = {
                    showOptionsVideoDialog = false
                    downloader.downloadVideo(selectedMessageText.toString())
                    Toast.makeText(
                        context,
                        "Downloading video.",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onDelete = {
                    showOptionsVideoDialog = false
                    showConfirmDeleteDialog = true
                },
                onDismiss = {
                    showOptionsVideoDialog = false
                }
            )
        }

        if (showConfirmDeleteDialog) {
            ConfirmDeleteDialog(
                showDialog = true,
                onConfirm = {
                    selectedMessageId?.let {
                        viewModel.deleteMessageFromFirebase(it)
                    }
                    showConfirmDeleteDialog = false
                },
                onDismiss = {
                    showConfirmDeleteDialog = false
                }
            )
        }

    }
}


@Composable
fun TopBarMes(
    navController: NavController,
    account: Account
) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row (
            modifier = Modifier
                .fillMaxHeight()
                .clickable {

                },
            verticalAlignment = Alignment.CenterVertically
        ){
            AvatarIcon(
                imageUrl = account.imageUri,
                modifier = Modifier
                    .width(65.dp)
                    .aspectRatio(1f),
                isOnline = account.activeStatus!="OFF" && account.status == "online"
            ) {}
            Column {
                TextNameUser(account.nickName)
                if(account.status == "online"){
                    Text(text = "Active now",
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.Gray
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                        )
                }else{
                    val timeAgo = account.timestamp?.let { getTimeAgo(it) }
                    Text(text = "$timeAgo",
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.Gray
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Row (
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            RoundIconButton(
                imageResId = R.drawable.info,
                imageVector = null,
                modifier = Modifier.size(50.dp)
            ) {
                navController.navigate(InfoScreenNavigation.createRoute(account.uid))
            }
        }
    }
}

fun getTimeAgo(lastOnlineTimestamp: Long): String {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - lastOnlineTimestamp

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)

    return when {
        minutes < 60 -> "Active $minutes minutes ago"
        hours < 24 -> "Active $hours hours ago"
        days < 7 -> "Active $days days ago"
        else -> "Active more than 7 days ago"
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MesPreview() {
        MessageScreen(
            viewModel(),
            downloader = Downloader(LocalContext.current),
            navController = rememberNavController(),
            curentid = "",
            friendid = "",
            context = LocalContext.current
        )
}