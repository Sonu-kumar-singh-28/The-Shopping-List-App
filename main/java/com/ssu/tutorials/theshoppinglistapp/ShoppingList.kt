package com.ssu.tutorials.theshoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignuiApp() {

    var numberitems by remember { mutableStateOf(listOf<Shoppingitem>()) }
    var showdailogvbo by remember { mutableStateOf(false) }
    var itemname by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = { showdailogvbo = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text("Add item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(numberitems) { item ->
                if (item.isEditing) {
                    ShoppingitemEditing(
                        item = item,
                        onEditComplete = { newName, newQty ->
                            numberitems = numberitems.map {
                                if (it.id == item.id) it.copy(
                                    name = newName,
                                    Quantity = newQty,
                                    isEditing = false
                                ) else it
                            }
                        }
                    )
                } else {
                    ShoppingitemDesign(
                        item = item,
                        onEditClick = {
                            numberitems = numberitems.map {
                                if (it.id == item.id) it.copy(isEditing = true) else it
                            }
                        },
                        onDeleteClick = { numberitems = numberitems - item }
                    )
                }
            }
        }

        // Add Dialog
        if (showdailogvbo) {
            AlertDialog(
                onDismissRequest = { showdailogvbo = false },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            if (itemname.isNotBlank() && itemQuantity.isNotBlank()) {
                                val newitem = Shoppingitem(
                                    id = numberitems.size + 1,
                                    name = itemname,
                                    Quantity = itemQuantity.toIntOrNull() ?: 0
                                )
                                numberitems = numberitems + newitem
                                showdailogvbo = false
                                itemname = ""
                                itemQuantity = ""
                            }
                        }) {
                            Text("Add")
                        }

                        Button(onClick = { showdailogvbo = false }) {
                            Text("Cancel")
                        }
                    }
                },
                title = { Text("Add Shopping Item") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = itemname,
                            onValueChange = { itemname = it },
                            label = { Text("Item Name") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )

                        OutlinedTextField(
                            value = itemQuantity,
                            onValueChange = { itemQuantity = it },
                            label = { Text("Quantity") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ShoppingitemEditing(
    item: Shoppingitem,
    onEditComplete: (String, Int) -> Unit
) {
    var ediname by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.Quantity.toString()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFECECEC))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = ediname,
            onValueChange = { ediname = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        BasicTextField(
            value = quantity,
            onValueChange = { quantity = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(onClick = {
            onEditComplete(ediname, quantity.toIntOrNull() ?: 1)
        }) {
            Text("Save")
        }
    }
}

@Composable
fun ShoppingitemDesign(
    item: Shoppingitem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(20)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Quantity: ${item.Quantity}", style = MaterialTheme.typography.bodyMedium)
        }

        Row {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

data class Shoppingitem(
    val id: Int,
    var name: String,
    var Quantity: Int,
    var isEditing: Boolean = false
)

@Preview
@Composable
private fun prevdesignuiapp() {
    DesignuiApp()
}
