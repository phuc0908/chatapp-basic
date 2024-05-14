package com.example.chatapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatapp.Destination
import com.example.chatapp_dacs3.R
import com.example.chatapp.ui.theme.Green1

@Composable
fun BottomNavigation(
    buttonIsClicked: Int,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val texts = listOf("Message", "Calls","Contacts", "Settings")
        val icons = listOf(
            painterResource(id = R.drawable.chat_bubble),
            painterResource(id = R.drawable.call),
            painterResource(id = R.drawable.contacts),
            painterResource(id = R.drawable.settings)
        )
        for (i in 0 .. 3) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable (onClick ={
                        when(i){
                            0 -> navController.navigate(Destination.Home.route)
                            1 -> navController.navigate(Destination.Call.route)
                            2 -> navController.navigate(Destination.Contact.route)
                            3 -> navController.navigate(Destination.Setting.route)

                        }
                    }),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = icons[i],
                    contentDescription = null,
                    tint =
                    if (i==buttonIsClicked) Green1
                    else Color.Gray,
                    modifier = Modifier.size(37.dp)
                )
                Text(text = texts[i],style = TextStyle(fontSize = 10.sp)
                    , color =
                    if (i==buttonIsClicked) Green1
                    else Color.Gray,
                )
            }
        }

    }
}