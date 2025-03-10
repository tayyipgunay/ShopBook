package com.tayyipgunay.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tayyipgunay.model.Item

@Composable
fun ItemList(ItemList: List<Item>,navController: NavController) {
    Scaffold(
        topBar = {},
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FAB {
                navController.navigate("addItemScreen")

            }
        }, content = { innerPadding ->

            LazyColumn(contentPadding = innerPadding, modifier= Modifier.fillMaxSize().
            background(color = MaterialTheme.colorScheme.primaryContainer))
            {
                items(ItemList) { item ->
                    ItemRow(item = item, navController = navController)
                }
            }
        }
    )
}



@Composable
fun ItemRow(item: Item,navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                // Burada tıklama işlemi yapılacak
                navController.navigate("detailsScreen/${item.id}")
            }
    ) {
        Text(
            text = item.itemName,
            modifier = Modifier.padding(2.dp),
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
        )
        //spacer


    }
}

@Composable
fun FAB(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}



