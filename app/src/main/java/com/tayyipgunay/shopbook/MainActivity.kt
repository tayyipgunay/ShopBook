package com.tayyipgunay.shopbook

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.tayyipgunay.model.Item
import com.tayyipgunay.screens.AddItemScreen
import com.tayyipgunay.screens.DetailsScreen
import com.tayyipgunay.screens.ItemList
import com.tayyipgunay.shopbook.ui.theme.ShopBookTheme
import com.tayyipgunay.viewmodel.ListViewModel
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    private val listViewModel : ListViewModel by viewModels<ListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ShopBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "listScreen"
                        ) {

                            composable("listScreen") {
                                listViewModel.getItemList()
                                val itemlist by remember {
                                    listViewModel.itemList
                                }
                                // println(itemlist.get(0).itemName?:"veri gelmedi")
                                ItemList(ItemList = itemlist, navController = navController)
                            }

                            composable("addItemScreen") {
                                AddItemScreen(saveFunction = { item ->
                                    listViewModel.insertItem(item)
                                    navController.navigate("listScreen")
                                }
                                )
                            }

                            composable(
                                "detailsScreen/{itemId}",
                                arguments = listOf(
                                    navArgument("itemId") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                val itemIdString = remember {
                                    it.arguments?.getString("itemId")
                                }
                                println("itemIdString: $itemIdString")

                                listViewModel.getItemById(itemIdString?.toIntOrNull() ?: 1)
                                val selectedItem by remember {
                                    listViewModel.selectedItem
                                }
                                println("selectedItem: ${selectedItem.itemName}")




                                DetailsScreen(item =selectedItem, deleteFunction = {
                                    listViewModel.deleteItem(selectedItem)
                                    navController.navigate("listScreen")
                                }
                                )
                            }


                        }

                    }


                }
            }
        }


    }





    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ShopBookTheme {
           DetailsScreen(item = Item("laptop", "Apple Store", byteArrayOf(),"$10.0"), deleteFunction = {})
            //ItemList(ItemList = listOf(Item("laptop", "Apple Store", byteArrayOf(),"$10.0")), navController = rememberNavController())






        }

    }
}




