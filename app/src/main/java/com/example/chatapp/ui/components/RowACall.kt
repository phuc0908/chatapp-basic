package com.example.chatapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatapp_dacs3.R

@Composable
fun RowACall(

) {
    Row (
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(100.dp)
    ){
//        left row
        Row(

        ){
            RoundIconButton(
                imageResId = R.drawable.rangdong,
                imageVector = null,
                modifier = Modifier.size(50.dp)
            ) {

            }
            Column(

            ){
               TextNameUser(name = "Phuc Is Me")
                Row {
                    RoundIconButton(
                        imageResId = R.drawable.call,
                        imageVector = null,
                        modifier = Modifier.size(10.dp)
                    ) {

                    }
                    TextChat(text = "Today, 9:30 AM")
                }
            }
        }
//        right row
        Row(

        ){
            RoundIconButton(
                imageResId = R.drawable.call,
                imageVector = null,
                modifier = Modifier.size(50.dp)
            ) {

            }
            RoundIconButton(
                imageResId = R.drawable.camera,
                imageVector = null,
                modifier = Modifier.size(50.dp)
            ) {

            }
        }
    }
}
