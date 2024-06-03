package com.example.chatapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun OptionsMesDialog(
    isMyMessage: Boolean,
    showDialog: Boolean,
    onCopy: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = onDismiss,
            title = {
                Text("Choose Options")
            },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    ElevatedButton(
                        onClick = onCopy,
                        Modifier.fillMaxWidth().align(Alignment.Start))
                    {
                        Text("Copy")
                    }
                    if(isMyMessage){
                        ElevatedButton(
                        onClick = onDelete,
                        Modifier.fillMaxWidth().align(Alignment.Start))
                        {
                            Text("Delete")
                        }
                    }

                }
            },
            confirmButton = {},
        )
    }
}

@Composable
fun OptionsImageDialog(
    isMyImage: Boolean,
    showDialog: Boolean,
    onDownload: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = onDismiss,
            title = {
                Text("Choose Options")
            },
            text = {
                Column(Modifier.fillMaxWidth()) {

                    if(isMyImage){
                        ElevatedButton(
                            onClick = onDelete,
                            Modifier.fillMaxWidth().align(Alignment.Start))
                        {
                            Text("Delete")
                        }
                    }else{
                        ElevatedButton(
                            onClick = onDownload,
                            Modifier.fillMaxWidth().align(Alignment.Start))
                        {
                            Text("Download")
                        }
                    }
                }
            },
            confirmButton = {},
        )
    }
}

@Composable
fun ConfirmDeleteDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit)
{
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Delete Message") },
            text = { Text(text = "Are you sure you want to delete this message?") },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}