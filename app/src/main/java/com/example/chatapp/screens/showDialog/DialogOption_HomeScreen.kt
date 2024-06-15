package com.example.chatapp.screens.showDialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OptionsHomeDialog(
    showDialog: Boolean,
    onSave: () -> Unit,
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
                        onClick = onSave,
                        Modifier.fillMaxWidth().align(Alignment.Start))
                    {
                        Text("Save")
                    }
                    ElevatedButton(
                        onClick = onDelete,
                        Modifier.fillMaxWidth().align(Alignment.Start))
                    {
                        Text("Delete")
                    }
                    ElevatedButton(
                        onClick = onDismiss,
                        Modifier.fillMaxWidth().align(Alignment.Start))
                    {
                        Text("Cancel")
                    }
                }
            },
            confirmButton = {},
        )
    }
}

@Composable
fun ConfirmDeleteHomeDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit)
{
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Delete this whole chat?") },
            text = { Text(text = "You can't undo after deleting this conversation?") },
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